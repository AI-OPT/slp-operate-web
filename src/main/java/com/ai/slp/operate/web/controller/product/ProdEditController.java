package com.ai.slp.operate.web.controller.product;

import com.ai.opt.sdk.components.dss.DSSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.dss.base.interfaces.IDSSClient;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.common.api.cache.param.SysParam;
import com.ai.slp.operate.web.constants.ComCacheConstants;
import com.ai.slp.operate.web.constants.ProductCatConstants;
import com.ai.slp.operate.web.constants.SysCommonConstants;
import com.ai.slp.operate.web.model.product.ProductEditInfo;
import com.ai.slp.product.api.normproduct.interfaces.INormProductSV;
import com.ai.slp.product.api.normproduct.param.AttrMap;
import com.ai.slp.product.api.normproduct.param.AttrQuery;
import com.ai.slp.product.api.normproduct.param.AttrValInfo;
import com.ai.slp.product.api.normproduct.param.ProdCatAttrInfo;
import com.ai.slp.product.api.product.interfaces.IProductManagerSV;
import com.ai.slp.product.api.product.interfaces.IProductSV;
import com.ai.slp.product.api.product.param.*;
import com.ai.slp.product.api.productcat.interfaces.IProductCatSV;
import com.ai.slp.product.api.productcat.param.ProductCatInfo;
import com.ai.slp.product.api.productcat.param.ProductCatUniqueReq;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * 销售商品编辑
 * Created by jackieliu on 16/6/16.
 */
@Controller
@RequestMapping("/prodedit")
public class ProdEditController {
    private static Logger logger = LoggerFactory.getLogger(ProdEditController.class);
    IProductManagerSV productManagerSV;
    IProductSV productSV;
    ICacheSV cacheSV;
    INormProductSV normProductSV;
    IProductCatSV productCatSV;

    public void initConsumer() {
        if (productManagerSV == null)
            productManagerSV = DubboConsumerFactory.getService(IProductManagerSV.class);
        if (productSV == null)
            productSV = DubboConsumerFactory.getService(IProductSV.class);
        if (cacheSV == null)
            cacheSV = DubboConsumerFactory.getService(ICacheSV.class);
        if (normProductSV == null)
            normProductSV = DubboConsumerFactory.getService(INormProductSV.class);
        if (productCatSV == null)
            productCatSV = DubboConsumerFactory.getService(IProductCatSV.class);
    }
    /**
     * 显示商品编辑页面
     * @param prodId
     * @return
     */
    @RequestMapping("/{id}")
    public String editView(@PathVariable("id")String prodId,String fileId,Model uiModel){
        initConsumer();
        //查询商品详情
        ProductInfoQuery infoQuery = new ProductInfoQuery();
        infoQuery.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
        infoQuery.setProductId(prodId);
        ProductInfo productInfo = productSV.queryProductById(infoQuery);
        uiModel.addAttribute("productInfo",productInfo);
        //查询类目链
        ProductCatUniqueReq catUniqueReq = new ProductCatUniqueReq();
        catUniqueReq.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
        catUniqueReq.setProductCatId(productInfo.getProductCatId());
        List<ProductCatInfo> catLinkList =productCatSV.queryLinkOfCatById(catUniqueReq);
        uiModel.addAttribute("catLinkList",catLinkList);
        //商品类型
        SysParam sysParam = cacheSV.getSysParam(
                SysCommonConstants.COMMON_TENANT_ID, ComCacheConstants.TypeProduct.CODE,
                ComCacheConstants.TypeProduct.PROD_PRODUCT_TYPE,productInfo.getProductType());
        uiModel.addAttribute("prodType",sysParam.getColumnDesc());
        //标准品关键属性
        AttrQuery attrQuery = new AttrQuery();
        attrQuery.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
        attrQuery.setProductId(productInfo.getStandedProdId());
        attrQuery.setAttrType(ProductCatConstants.ProductCatAttr.AttrType.ATTR_TYPE_KEY);
        AttrMap attrMap = normProductSV.queryAttrByNormProduct(attrQuery);
        uiModel.addAttribute("attrAndVal",getAttrAndVals(attrMap));
        //商品非关键属性
        ProdNoKeyAttr noKeyAttr = productManagerSV.queryNoKeyAttrOfProd(infoQuery);
        uiModel.addAttribute("noKeyAttr",noKeyAttr.getAttrMap());
        //查询商品其他设置
        OtherSetOfProduct otherSet = productManagerSV.queryOtherSetOfProduct(infoQuery);
        uiModel.addAttribute("otherSet",otherSet);
        //个人受众
        ProdAudiencesInfo audiPerson = otherSet.getPersonAudiences();
        uiModel.addAttribute("audiPerson",audiPerson==null?"0":audiPerson.getUserId());
        //企业受众
        Map<String,ProdAudiencesInfo> entMap = otherSet.getEnterpriseMap();
        entMap = genDemoAudiData(20);//TODO... 正式需删除
        uiModel.addAttribute("audiEnt",audiType(entMap));
        uiModel.addAttribute("audiEnts",audiStr(entMap));
        //代理商受众
        Map<String,ProdAudiencesInfo> agentMap = otherSet.getEnterpriseMap();
        agentMap = genDemoAudiData(35);//TODO... 正式需删除
        uiModel.addAttribute("audiAgent",audiType(agentMap));
        uiModel.addAttribute("audiAgents",audiStr(agentMap));

        //商品主图
        uiModel.addAttribute("prodPic",otherSet.getProductPics());

        //有效期单位
        List<SysParam> prodUnits = cacheSV.getSysParams(SysCommonConstants.COMMON_TENANT_ID,
                ComCacheConstants.TypeProduct.CODE,ComCacheConstants.TypeProduct.PROD_UNIT);
        uiModel.addAttribute("prodUnits",prodUnits);
        //运营商
        List<SysParam> basicOrgIds = cacheSV.getSysParams(SysCommonConstants.COMMON_TENANT_ID,
                ComCacheConstants.TypeProduct.CODE,ComCacheConstants.TypeProduct.BASIC_ORG_ID);
        uiModel.addAttribute("orgIds",basicOrgIds);
        //设置商品详情
        setProdDetail(fileId,uiModel);
        return "product/edit";
    }

    /**
     * 保持编辑信息
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResponseData<String> saveProductInfo(ProductEditInfo editInfo, String detailConVal, RedirectAttributes redirectModel){
        ResponseData<String> responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "添加成功");
        initConsumer();

        IDSSClient client= DSSClientFactory.getDSSClient(SysCommonConstants.ProductDetail.DSSNS);
        String fileId = editInfo.getProDetailContent();
        //若已经存在,则直接删除
        if (StringUtils.isNotBlank(fileId) && client.isIndexExist(fileId)){
            client.deleteById(fileId);
            fileId = "";
        }
        //TODO... 正式环境需要取消注释
//        if (StringUtils.isNotBlank(detailConVal))
//            fileId = client.insert(detailConVal);
//        logger.info("fileId="+fileId);
        editInfo.setProDetailContent(fileId);
        ProductInfoForUpdate prodInfo = new ProductInfoForUpdate();
        BeanUtils.copyProperties(prodInfo,editInfo);
        //添加省份编码
        if ("N".equals(editInfo.getIsSaleNationwide()) && StringUtils.isNotBlank(editInfo.getTargetProd()))
            prodInfo.setProvCodes(JSON.parseArray(editInfo.getTargetProd(),Long.class));
        //添加企业受众
        if ("1".equals(editInfo.getAudiencesEnterprise()) && StringUtils.isNotBlank(editInfo.getAudiEntIds()))
            prodInfo.setEnterpriseIds(JSON.parseArray(editInfo.getAudiEntIds(),String.class));
        //代理商受众
        if ("1".equals(editInfo.getAudiencesAgents()) && StringUtils.isNotBlank(editInfo.getAudiAgentIds()))
            prodInfo.setAgentIds(JSON.parseArray(editInfo.getAudiAgentIds(),String.class));

        //保存商品详情信息 TODO...
//        BaseResponse response = productManagerSV.updateProduct(prodInfo);
//        ResponseHeader header = response.getResponseHeader();
//
//        //保存错误
//        if (header!=null && !header.isSuccess()){
//            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "添加失败:"+header.getResultMessage());
//        }
        return responseData;
    }

    private Map<ProdCatAttrInfo,List<AttrValInfo>> getAttrAndVals(AttrMap attrMap){
        Map<ProdCatAttrInfo,List<AttrValInfo>> attrAndValMap = new HashMap<>();
        Set<Map.Entry<Long,List<Long>>> entrySet = attrMap.attrAndVal.entrySet();
        for (Map.Entry<Long,List<Long>> mapEntry:entrySet){
            ProdCatAttrInfo attrInfo = attrMap.getAttrDefMap().get(mapEntry.getKey());
            List<AttrValInfo> valInfoList = new ArrayList<AttrValInfo>();
            List<Long> valIds = mapEntry.getValue();
            for (Long valId:valIds){
                valInfoList.add(attrMap.getAttrValDefMap().get(valId));
            }
            attrAndValMap.put(attrInfo, valInfoList);
        }
        return attrAndValMap;
    }

    public void setProdDetail(String fileId,Model uiModel){
        if (StringUtils.isBlank(fileId)){
            return;
        }
        IDSSClient client= DSSClientFactory.getDSSClient(SysCommonConstants.ProductDetail.DSSNS);
        byte[] prodDetail = client.read(fileId);
        uiModel.addAttribute("prodDetail",new String(prodDetail));
    }

    private String audiType(Map<String,ProdAudiencesInfo> audiMap){
        //默认不分可见
        String audiEnt = "1";
        //代理商受众
        //为空表示全部不可见
        if (audiMap==null || audiMap.isEmpty()){
            audiEnt = "0";
        }
        //包含-1表示全部可见
        else if(audiMap.containsKey("-1")){
            audiEnt = "-1";
        }
        return audiEnt;
    }

    /**
     * 获取受众json格式字符串
     * @param audiMap
     * @return
     */
    private String audiStr(Map<String,ProdAudiencesInfo> audiMap){
        Map<String,String> strMap = new HashMap<>();
        //不是全部可见,且具有受众用户
        if (audiMap!=null && !audiMap.isEmpty() && !audiMap.containsKey("-1")){
            Set<Map.Entry<String,ProdAudiencesInfo>> mapEntry = audiMap.entrySet();
            for (Map.Entry<String,ProdAudiencesInfo> entry:mapEntry){
                strMap.put(entry.getKey(),entry.getValue().getUserName());
            }
        }
        return JSON.toJSONString(strMap);
    }

    /**
     * 产生测试数据
     * @return
     */
    private Map<String,ProdAudiencesInfo> genDemoAudiData(int num){
        Map<String,ProdAudiencesInfo> audiMap = new HashMap<>();
        for (int i=0;i<num;i++){
            ProdAudiencesInfo audInfo = new ProdAudiencesInfo();
            audInfo.setUserId(i+"id");
            audInfo.setUserName("test"+i);
            audiMap.put(audInfo.getUserId(),audInfo);
        }
        return audiMap;
    }
}

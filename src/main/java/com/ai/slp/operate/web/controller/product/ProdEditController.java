package com.ai.slp.operate.web.controller.product;

import com.ai.opt.sdk.components.dss.DSSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
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
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * 销售商品编辑
 * Created by jackieliu on 16/6/16.
 */
@Controller
@RequestMapping("/prodedit")
public class ProdEditController {
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
    public String saveProductInfo(ProductEditInfo editInfo, String detailConVal, Model uiModel){
        initConsumer();
        ProductInfoForUpdate prodInfo = new ProductInfoForUpdate();

        //保存商品详情信息
        IDSSClient client= DSSClientFactory.getDSSClient(SysCommonConstants.ProductDetail.DSSNS);
        String fileId = client.save(detailConVal.getBytes(),System.currentTimeMillis()+"");
        System.out.println("fileId="+fileId);
        return "redirect:/prodedit/"+editInfo.getProdId()+"?fileId="+fileId;
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

}

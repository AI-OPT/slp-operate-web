package com.ai.slp.operate.web.controller.normproduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.paas.ipaas.util.JSonUtil;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.operate.web.constants.ProductCatConstants;
import com.ai.slp.operate.web.constants.SysCommonConstants;
import com.ai.slp.operate.web.controller.product.ProdEditController;
import com.ai.slp.operate.web.vo.ProdQueryCatVo;
import com.ai.slp.product.api.normproduct.interfaces.INormProductSV;
import com.ai.slp.product.api.product.interfaces.IProductManagerSV;
import com.ai.slp.product.api.product.interfaces.IProductSV;
import com.ai.slp.product.api.productcat.interfaces.IProductCatSV;
import com.ai.slp.product.api.productcat.param.ProdCatInfo;
import com.ai.slp.product.api.productcat.param.ProductCatInfo;
import com.ai.slp.product.api.productcat.param.ProductCatQuery;
import com.ai.slp.product.api.productcat.param.ProductCatUniqueReq;

/**
 * 对标准品进行操作
 * @author jiawen
 *
 */
@Controller
@RequestMapping("/normprodedit")
public class NormProdEditController {
	
	private static final Logger LOG = LoggerFactory.getLogger(NormProdEditController.class);

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
     * 跳转到新增页面
     * @param normprodId
     * @return
     */
    @RequestMapping("/add")
    public String addView(Model uiModel){
    	loadCat(uiModel);
    	return "normproduct/add";
    }
    
    private void loadCat(Model uiModel) {
		IProductCatSV productCatSV = DubboConsumerFactory.getService("iProductCatSV");
		ProductCatQuery catQuery = new ProductCatQuery();
		catQuery.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
		Map<Short, List<ProdCatInfo>> productCatMap = new HashMap<>();
		ProdCatInfo prodCatInfo = null;
		do {
			// 查询同一级的类目信息
			List<ProdCatInfo> productCatInfos = productCatSV.queryCatByNameOrFirst(catQuery);
			prodCatInfo = productCatInfos.get(0);
			// 把类目信息按照类目等级放入集合
			productCatMap.put(prodCatInfo.getCatLevel(), productCatInfos);
			catQuery.setParentProductCatId(prodCatInfo.getProductCatId());
		} while (prodCatInfo.getIsChild().equals(ProductCatConstants.ProductCat.IsChild.HAS_CHILD));
		uiModel.addAttribute("count", productCatMap.size() - 1);
		uiModel.addAttribute("catInfoMap", productCatMap);
	}
    
    /**
	 * 类目联动调用方法
	 * 
	 * @return
	 */
	@RequestMapping("/getCat")
	@ResponseBody
	public List<ProdQueryCatVo> changeCat(HttpServletRequest request) {
		List<ProdQueryCatVo> prodQueryCatVoList = new ArrayList<>();
		try {
			IProductCatSV productCatSV = DubboConsumerFactory.getService("iProductCatSV");
			//通过id查询当前类目信息
			ProductCatUniqueReq productCatUniqueReq = new ProductCatUniqueReq();
			productCatUniqueReq.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
			String prodCatId = request.getParameter("prodCatId");
			productCatUniqueReq.setProductCatId(prodCatId);
			ProductCatInfo productCatInfo = productCatSV.queryByCatId(productCatUniqueReq);
			ProductCatQuery catQuery = new ProductCatQuery();
			ProdCatInfo prodCatInfo = null;
			catQuery.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
			//如果当前类目有子类则查询下一级类目
			if(productCatInfo.getIsChild().equals(ProductCatConstants.ProductCat.IsChild.HAS_CHILD)){
				catQuery.setParentProductCatId(prodCatId);
				do{
					// 查询同一级的类目信息
					List<ProdCatInfo> productCatInfos = productCatSV.queryCatByNameOrFirst(catQuery);
					prodCatInfo = productCatInfos.get(0);
					ProdQueryCatVo prodQueryCatVo = new ProdQueryCatVo();
					prodQueryCatVo.setLevel((short)(prodCatInfo.getCatLevel()-1));
					prodQueryCatVo.setProdCatList(productCatInfos);
					prodQueryCatVoList.add(prodQueryCatVo);
					catQuery.setParentProductCatId(prodCatInfo.getProductCatId());
				}while(prodCatInfo.getIsChild().equals(ProductCatConstants.ProductCat.IsChild.HAS_CHILD));
			}
			LOG.debug("获取类目信息出参:" + JSonUtil.toJSon(prodQueryCatVoList));
	    } catch (Exception e) {
	    	prodQueryCatVoList = null;
	        LOG.error("获取类目信息出错", e);
	    }
		return prodQueryCatVoList;
	}
    
    /**
     * 跳转页面
     * @param normprodId
     * @return
     */
    @RequestMapping("/addinfo")
    public String addinfoView(){
    	return "normproduct/addinfo";
    }
    
}

package com.ai.slp.operate.web.controller.normproduct;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfoResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.paas.ipaas.util.JSonUtil;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.common.api.cache.param.SysParamSingleCond;
import com.ai.slp.operate.web.constants.ComCacheConstants;
import com.ai.slp.operate.web.constants.ProductCatConstants;
import com.ai.slp.operate.web.constants.SysCommonConstants;
import com.ai.slp.operate.web.util.AdminUtil;
import com.ai.slp.operate.web.util.DateUtil;
import com.ai.slp.operate.web.vo.ProdQueryCatVo;
import com.ai.slp.product.api.normproduct.interfaces.INormProductSV;
import com.ai.slp.product.api.normproduct.param.NormProdRequest;
import com.ai.slp.product.api.normproduct.param.NormProdResponse;
import com.ai.slp.product.api.product.interfaces.IProductManagerSV;
import com.ai.slp.product.api.product.param.ProductEditQueryReq;
import com.ai.slp.product.api.product.param.ProductEditUp;
import com.ai.slp.product.api.product.param.ProductInfoQuery;
import com.ai.slp.product.api.product.param.ProductStorageSale;
import com.ai.slp.product.api.product.param.ProductStorageSaleParam;
import com.ai.slp.product.api.productcat.interfaces.IProductCatSV;
import com.ai.slp.product.api.productcat.param.ProdCatInfo;
import com.ai.slp.product.api.productcat.param.ProductCatInfo;
import com.ai.slp.product.api.productcat.param.ProductCatQuery;
import com.ai.slp.product.api.productcat.param.ProductCatUniqueReq;

/**
 * 标准品查询
 * @author jiawen
 */
@Controller
@RequestMapping("/normprodquery")
public class NormProdQueryController {
	private static final Logger LOG = LoggerFactory.getLogger(NormProdQueryController.class);

	/**
	 * 进入页面调用-加载类目
	 */
	@RequestMapping("/list")
	public String editQuery(Model uiModel) {
		loadCat(uiModel);
		return "normproduct/normproductlist";
	}
	
	/**
	 * 点击查询按钮调用方法-查询标准品
	 * @return
	 */
	@RequestMapping("/getNormProductList")
	@ResponseBody
	private ResponseData<PageInfoResponse<NormProdResponse>> queryNormProduct(HttpServletRequest request,NormProdRequest productRequest){
		ResponseData<PageInfoResponse<NormProdResponse>> responseData = null;
		try {
			//查询条件
			queryBuilder(request, productRequest);
			
			PageInfoResponse<NormProdResponse> result = queryProductByState(productRequest);
			responseData = new ResponseData<PageInfoResponse<NormProdResponse>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",
					result);
		} catch (Exception e) {
			responseData = new ResponseData<PageInfoResponse<NormProdResponse>>(ResponseData.AJAX_STATUS_FAILURE,
					"获取信息异常");
			LOG.error("获取信息出错：", e);
		}
		return responseData;
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
	 * 根据状态不同查询商品
	 * 
	 * @param 
	 * @return
	 */
	private PageInfoResponse<NormProdResponse> queryProductByState(NormProdRequest productRequest) {
		INormProductSV normProductSV = DubboConsumerFactory.getService(INormProductSV.class);
		PageInfoResponse<NormProdResponse> result = normProductSV.queryNormProduct(productRequest);
		ICacheSV cacheSV = DubboConsumerFactory.getService("iCacheSV");
		SysParamSingleCond sysParamSingleCond = null;
		for (NormProdResponse normProdResponse : result.getResult()) {
			// 获取类型和状态
			if (StringUtils.isNotBlank(normProdResponse.getProductType())) {
				// 获取类型
				String productType = normProdResponse.getProductType();
				sysParamSingleCond = new SysParamSingleCond(SysCommonConstants.COMMON_TENANT_ID,
						ComCacheConstants.TypeProduct.CODE, ComCacheConstants.TypeProduct.PROD_PRODUCT_TYPE,
						productType);
				String productTypeName = cacheSV.getSysParamSingle(sysParamSingleCond).getColumnDesc();
				normProdResponse.setProductType(productTypeName);
				// 获取状态
				String state = normProdResponse.getState();
				sysParamSingleCond = new SysParamSingleCond(SysCommonConstants.COMMON_TENANT_ID,
						ComCacheConstants.NormProduct.CODE, ComCacheConstants.NormProduct.STATUS, state);
				String stateName = cacheSV.getSysParamSingle(sysParamSingleCond).getColumnDesc();
				normProdResponse.setState(stateName);
			}
			
		}
		return result;
	}
	
	/**
	 * 查询条件检查设置  
	 */
	private void queryBuilder(HttpServletRequest request,NormProdRequest productRequest) {
		productRequest.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
		productRequest.setProductCatId(request.getParameter("productCatId"));
		
		if (StringUtils.isNotBlank(request.getParameter("state")))
			productRequest.setState(request.getParameter("state"));
		if(!request.getParameter("productId").isEmpty())
			productRequest.setStandedProdId(request.getParameter("productId"));
		if(!request.getParameter("productName").isEmpty())
			productRequest.setStandedProductName(request.getParameter("productName"));
		
		
		if (StringUtils.isNotBlank(request.getParameter("operStartTimeStr"))) {
			String startTime = request.getParameter("operStartTimeStr")+" 00:00:00";
			productRequest.setOperStartTime(DateUtil.getTimestamp(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		
		if (StringUtils.isNotBlank(request.getParameter("operEndTimeStr"))) {
				String endTime = request.getParameter("operEndTimeStr")+" 23:59:59";
				productRequest.setOperEndTime(DateUtil.getTimestamp(endTime, "yyyy-MM-dd HH:mm:ss"));
			}
		
	}
	
}

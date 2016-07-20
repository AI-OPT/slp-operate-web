package com.ai.slp.operate.web.controller.storage;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.opt.base.vo.PageInfoResponse;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.common.api.cache.param.SysParamSingleCond;
import com.ai.slp.operate.web.constants.ComCacheConstants;
import com.ai.slp.operate.web.constants.ProductCatConstants;
import com.ai.slp.operate.web.constants.SysCommonConstants;
import com.ai.slp.operate.web.controller.product.ProdQueryController;
import com.ai.slp.product.api.normproduct.interfaces.INormProductSV;
import com.ai.slp.product.api.normproduct.param.NormProdRequest;
import com.ai.slp.product.api.normproduct.param.NormProdResponse;
import com.ai.slp.product.api.productcat.interfaces.IProductCatSV;
import com.ai.slp.product.api.productcat.param.ProdCatInfo;
import com.ai.slp.product.api.productcat.param.ProductCatQuery;

@Controller
@RequestMapping("/storage")
public class StorageController {
		private static final Logger LOG = LoggerFactory.getLogger(ProdQueryController.class);

		/**
		 * 进入页面调用-加载类目
		 */
		@RequestMapping("/prodstorage")
		public String editQuery(Model uiModel) {
			loadCat(uiModel);
			return "storage/prodstorage";
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
		 * 查询标准品
		 * @param request
		 * @param productRequest
		 * @return
		 */
		@RequestMapping("/normProdList")
		@ResponseBody
		public  ResponseData<PageInfoResponse<NormProdResponse>> getNormProdList(HttpServletRequest request,NormProdRequest productRequest){
			ResponseData<PageInfoResponse<NormProdResponse>> responseData = null;
			try {
				//查询条件
				queryBuilder(request, productRequest);
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
						normProdResponse.setProductTypeName(productTypeName);
					}
				}
				responseData = new ResponseData<PageInfoResponse<NormProdResponse>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",result);
			} catch (Exception e) {
				responseData = new ResponseData<PageInfoResponse<NormProdResponse>>(ResponseData.AJAX_STATUS_FAILURE,
						"获取信息异常");
				LOG.error("获取信息出错：", e);
			}
			return responseData;
		}
		/**
		 * 查询条件检查
		 * @param request
		 * @param productRequest
		 */
		private void queryBuilder(HttpServletRequest request, NormProdRequest productRequest) {
			productRequest.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
			productRequest.setProductCatId(request.getParameter("productCatId"));
			if(!request.getParameter("standedProductName").isEmpty())
				productRequest.setProductType(request.getParameter("standedProductName"));
			if(!request.getParameter("standedProdId").isEmpty())
				productRequest.setStandedProdId(request.getParameter("standedProdId"));
			if(!request.getParameter("productType").isEmpty())
				productRequest.setProductType(request.getParameter("productType"));
			if(!request.getParameter("startTime").isEmpty())
				productRequest.setOperStartTime(Timestamp.valueOf(request.getParameter("startTime")));
			if(!request.getParameter("endTime").isEmpty())
				productRequest.setOperStartTime(Timestamp.valueOf(request.getParameter("endTime")));
		}
}

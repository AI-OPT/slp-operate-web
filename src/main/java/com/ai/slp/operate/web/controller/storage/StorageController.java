package com.ai.slp.operate.web.controller.storage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfoResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.common.api.cache.param.SysParam;
import com.ai.slp.common.api.cache.param.SysParamSingleCond;
import com.ai.slp.operate.web.constants.ComCacheConstants;
import com.ai.slp.operate.web.constants.ProductCatConstants;
import com.ai.slp.operate.web.constants.SysCommonConstants;
import com.ai.slp.operate.web.controller.product.ProdQueryController;
import com.ai.slp.operate.web.util.AdminUtil;
import com.ai.slp.product.api.normproduct.interfaces.INormProductSV;
import com.ai.slp.product.api.normproduct.param.AttrMap;
import com.ai.slp.product.api.normproduct.param.AttrQuery;
import com.ai.slp.product.api.normproduct.param.AttrValInfo;
import com.ai.slp.product.api.normproduct.param.NormProdInfoResponse;
import com.ai.slp.product.api.normproduct.param.NormProdRequest;
import com.ai.slp.product.api.normproduct.param.NormProdResponse;
import com.ai.slp.product.api.normproduct.param.NormProdUniqueReq;
import com.ai.slp.product.api.normproduct.param.ProdCatAttrInfo;
import com.ai.slp.product.api.productcat.interfaces.IProductCatSV;
import com.ai.slp.product.api.productcat.param.ProdCatInfo;
import com.ai.slp.product.api.productcat.param.ProductCatInfo;
import com.ai.slp.product.api.productcat.param.ProductCatQuery;
import com.ai.slp.product.api.productcat.param.ProductCatUniqueReq;
import com.ai.slp.product.api.storage.interfaces.IStorageSV;
import com.ai.slp.product.api.storage.param.STOStorageGroup;
import com.ai.slp.product.api.storage.param.StorageGroupQuery;
import com.ai.slp.product.api.storage.param.StorageGroupRes;

@Controller
@RequestMapping("/storage")
public class StorageController {
		private static final Logger LOG = LoggerFactory.getLogger(ProdQueryController.class);
		
		 /**
	     * 显示商品编辑页面
	     * @param prodId
	     * @return
	     */
	    @RequestMapping("/{id}")
	    public String storageEdit(@PathVariable("id")String standedProdId,Model uiModel){ 
	    	//标准品ID
	    	uiModel.addAttribute("standedProdId",standedProdId);
	    	//查询标准品信息
	    	NormProdUniqueReq normProdUniqueReq = new NormProdUniqueReq();
	    	normProdUniqueReq.setProductId(standedProdId);
	    	normProdUniqueReq.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
	    	INormProductSV normProductSV = DubboConsumerFactory.getService(INormProductSV.class);
	    	NormProdInfoResponse normProdInfoResponse = normProductSV.queryProducById(normProdUniqueReq);
	    	uiModel.addAttribute("normProdInfo",normProdInfoResponse);
	    	//查询类目链
	        ProductCatUniqueReq catUniqueReq = new ProductCatUniqueReq();
	        catUniqueReq.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
	        catUniqueReq.setProductCatId(normProdInfoResponse.getProductCatId());
	        IProductCatSV productCatSV = DubboConsumerFactory.getService(IProductCatSV.class);
	        List<ProductCatInfo> catLinkList =productCatSV.queryLinkOfCatById(catUniqueReq);
	        uiModel.addAttribute("catLinkList",catLinkList);
	        //商品类型
	        SysParamSingleCond paramSingleCond = new SysParamSingleCond();
	        paramSingleCond.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
	        paramSingleCond.setTypeCode(ComCacheConstants.TypeProduct.CODE);
	        paramSingleCond.setParamCode(ComCacheConstants.TypeProduct.PROD_PRODUCT_TYPE);
	        paramSingleCond.setColumnValue(normProdInfoResponse.getProductType());
	        ICacheSV cacheSV = DubboConsumerFactory.getService(ICacheSV.class);
	        SysParam sysParam = cacheSV.getSysParamSingle(paramSingleCond);
	        uiModel.addAttribute("prodType",sysParam.getColumnDesc());
	        //标准品关键属性
	        AttrQuery attrQuery = new AttrQuery();
	        attrQuery.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
	        attrQuery.setProductId(normProdInfoResponse.getProductId());
	        attrQuery.setAttrType(ProductCatConstants.ProductCatAttr.AttrType.ATTR_TYPE_KEY);
	        AttrMap attrMap = normProductSV.queryAttrByNormProduct(attrQuery);
	        uiModel.addAttribute("attrAndVal",getAttrAndVals(attrMap));
	        //查询库存组和库存信息
	        StorageGroupQuery storageGroupQuery = new StorageGroupQuery();
	        storageGroupQuery.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
	        storageGroupQuery.setProductId(normProdInfoResponse.getProductId());
	        IStorageSV storageSV = DubboConsumerFactory.getService(IStorageSV.class);
	        List<StorageGroupRes> storageGroupResList = storageSV.queryGroupInfoByNormProdId(storageGroupQuery);
	        uiModel.addAttribute("storGroupList",storageGroupResList);
	    	return "storage/storageEdit";
	    }
	    @RequestMapping("/addStorGroup")
	    @ResponseBody
	    public ResponseData<String> addStorGroup(HttpServletRequest request, HttpSession session){
	    	ResponseData<String> responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "添加成功");
	    	IStorageSV storageSV = DubboConsumerFactory.getService(IStorageSV.class);
	    	STOStorageGroup storageGroup = new STOStorageGroup();
	    	storageGroup.setCreateId(AdminUtil.getAdminId(session));
	    	storageGroup.setStandedProdId(request.getParameter("standedProdId"));
	    	storageGroup.setStorageGroupName(request.getParameter("storageGroupName"));
	    	storageGroup.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
	    	
	    	BaseResponse baseResponse = storageSV.createStorageGroup(storageGroup);
	    	ResponseHeader header = baseResponse.getResponseHeader();
	    	if (header!=null && !header.isSuccess()){
	            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "更新失败:"+header.getResultMessage());
	        }
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

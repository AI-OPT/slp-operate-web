package com.ai.slp.operate.web.controller.storage;

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
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.util.JSonUtil;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.common.api.cache.param.SysParam;
import com.ai.slp.common.api.cache.param.SysParamSingleCond;
import com.ai.slp.operate.web.constants.ComCacheConstants;
import com.ai.slp.operate.web.constants.ProductCatConstants;
import com.ai.slp.operate.web.constants.SysCommonConstants;
import com.ai.slp.operate.web.controller.product.ProdQueryController;
import com.ai.slp.operate.web.model.storage.StorageInfo;
import com.ai.slp.operate.web.util.AdminUtil;
import com.ai.slp.operate.web.util.DateUtil;
import com.ai.slp.operate.web.vo.ProdQueryCatVo;
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
import com.ai.slp.product.api.storage.param.STOStorage;
import com.ai.slp.product.api.storage.param.STOStorageGroup;
import com.ai.slp.product.api.storage.param.StorageGroupQuery;
import com.ai.slp.product.api.storage.param.StorageGroupRes;
import com.ai.slp.product.api.storage.param.StorageRes;

@Controller
@RequestMapping("/storage")
public class StorageController {
		private static final Logger LOG = LoggerFactory.getLogger(ProdQueryController.class);
		
		 /**
	     * 显示标准品库存编辑页面
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
	        uiModel.addAttribute("productCatId",normProdInfoResponse.getProductCatId());
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
	        for(StorageGroupRes storageGroupRes :storageGroupResList){
	        	// 获取库存组状态名
				String state = storageGroupRes.getState();
				paramSingleCond = new SysParamSingleCond(SysCommonConstants.COMMON_TENANT_ID,
						ComCacheConstants.StateStorage.STORAGEGROUP_TYPR_CODE, ComCacheConstants.StateStorage.PARAM_CODE, state);
				String stateName = cacheSV.getSysParamSingle(paramSingleCond).getColumnDesc();
				storageGroupRes.setStateName(stateName);
				// 获取库存状态名
				for(Short key : storageGroupRes.getStorageList().keySet()){
					for(StorageRes storageRes :storageGroupRes.getStorageList().get(key)){
						String storState = storageRes.getState();
						paramSingleCond = new SysParamSingleCond(SysCommonConstants.COMMON_TENANT_ID,
								ComCacheConstants.StateStorage.STORAGE_TYPR_CODE, ComCacheConstants.StateStorage.PARAM_CODE, storState);
						String storStateName = cacheSV.getSysParamSingle(paramSingleCond).getColumnDesc();
						storageRes.setStateName(storStateName);
					}
				}
	        }
	        uiModel.addAttribute("storGroupList",storageGroupResList);
	    	return "storage/storageEdit";
	    }
	    /**
	     * 添加库存组
	     * @param request
	     * @param session
	     * @return
	     */
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
//	    	if (header!=null && !header.isSuccess()){
//	    		return new ResponseData<StorageGroupRes>(ResponseData.AJAX_STATUS_FAILURE, "添加失败:"+header.getResultMessage());
//	        }
//	    	//通过ID查询库存组信息
//	    	StorageGroupQuery storageGroupQuery = new StorageGroupQuery();
//	    	storageGroupQuery.setGroupId(header.getResultMessage());
//	    	storageGroupQuery.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
//	    	StorageGroupRes storageGroupRes = storageSV.queryGroupInfoByGroupId(storageGroupQuery);
//	    	ICacheSV cacheSV = DubboConsumerFactory.getService(ICacheSV.class);
//	    	//设置状态名
//	    	String storGroupState = storageGroupRes.getState();
//	    	SysParamSingleCond paramSingleCond = new SysParamSingleCond(SysCommonConstants.COMMON_TENANT_ID,
//	    			ComCacheConstants.StateStorage.STORAGEGROUP_TYPR_CODE, ComCacheConstants.StateStorage.PARAM_CODE,storGroupState);
//			String storGroupStateName = cacheSV.getSysParamSingle(paramSingleCond).getColumnDesc();
//			storageGroupRes.setStateName(storGroupStateName);
//	    	return new ResponseData<StorageGroupRes>(ResponseData.AJAX_STATUS_FAILURE, "更新成功:"+header.getResultMessage(),storageGroupRes);
	    }
	    
	    /**
	     * 添加库存
	     * @param request
	     * @param session
	     * @return
	     */
	    @RequestMapping("/addStorage")
	    @ResponseBody
	    public ResponseData<String> addStorage(HttpServletRequest request, HttpSession session){
	    	ResponseData<String> responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "添加成功");
	    	IStorageSV storageSV = DubboConsumerFactory.getService(IStorageSV.class);
	    	STOStorage storage = new STOStorage();
	    	storage.setOperId(AdminUtil.getAdminId(session));
	    	storage.setProductCatId(request.getParameter("productCatId"));
	    	storage.setStorageName(request.getParameter("storageName"));
	    	storage.setStorageGroupId(request.getParameter("storGroupId"));
	    	storage.setPriorityNumber(Short.parseShort(request.getParameter("priorityNumber")));
	    	storage.setTotalNum(Long.parseLong(request.getParameter("totalNum")));
	    	storage.setWarnNum(Long.parseLong(request.getParameter("warnNum")));
	    	storage.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
	    	BaseResponse baseResponse = storageSV.saveStorage(storage);
	    	ResponseHeader header = baseResponse.getResponseHeader();
	    	if (header!=null && !header.isSuccess()){
	            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "更新失败:"+header.getResultMessage());
	        }
	    	return responseData;
//	    	String storageId = header.getResultMessage();
//	    	if (header!=null && !header.isSuccess()){
//	    		return new ResponseData<StorageInfo>(ResponseData.AJAX_STATUS_FAILURE, "更新失败:"+header.getResultMessage());
//	        }
//	    	int number = Integer.parseInt(request.getParameter("number"));
//	    	StorageRes storageRes = storageSV.queryStorageById(storageId);
//	    	StorageInfo storageInfo = new StorageInfo();
//	    	BeanUtils.copyProperties(storageInfo, storageRes);
//	    	storageInfo.setNumber(number);
//	    	ICacheSV cacheSV = DubboConsumerFactory.getService(ICacheSV.class);
//	    	String storState = storageInfo.getState();
//	    	SysParamSingleCond paramSingleCond = new SysParamSingleCond(SysCommonConstants.COMMON_TENANT_ID,
//					ComCacheConstants.StateStorage.STORAGE_TYPR_CODE, ComCacheConstants.StateStorage.PARAM_CODE, storState);
//			String storStateName = cacheSV.getSysParamSingle(paramSingleCond).getColumnDesc();
//			storageInfo.setStateName(storStateName);
//	    	return new ResponseData<StorageInfo>(ResponseData.AJAX_STATUS_SUCCESS, "更新成功:"+header.getResultMessage(),storageInfo);
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
		@RequestMapping("/list")
		public String editQuery(Model uiModel) {
			loadCat(uiModel);
			return "storage/prodstorage";
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

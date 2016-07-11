package com.ai.slp.operate.web.controller.product;

import java.util.ArrayList;
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
import com.ai.slp.operate.web.vo.ProdQueryCatVo;
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
 * 商城商品管理查询 Created by jackieliu on 16/6/16.
 */
@Controller
@RequestMapping("/prodquery")
public class ProdQueryController {
	private static final Logger LOG = LoggerFactory.getLogger(ProdQueryController.class);

	/**
	 * 进入页面调用-加载类目
	 */
	@RequestMapping("/add")
	public String editQuery(Model uiModel) {
		loadCat(uiModel);
		return "product/addlist";
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
	 * 进入页面调用-加载类目
	 */
	@RequestMapping("/storprod")
	public String storProdQuery(Model uiModel) {
		loadCat(uiModel);
		return "product/storprodlist";
	}
	
	/**
	 * 进入在售商品页面
	 * 
	 */
	@RequestMapping("/insale")
	public String inSalelistQuery(Model uiModel) {
		loadCat(uiModel);
		return "product/insalelist";
	}

	/**
	 * 根据状态不同查询商品
	 * 
	 * @param productEditQueryReq
	 * @return
	 */
	private PageInfoResponse<ProductEditUp> queryProductByState(ProductEditQueryReq productEditQueryReq) {
		IProductManagerSV productManagerSV = DubboConsumerFactory.getService("iProductManagerSV");
		PageInfoResponse<ProductEditUp> result = productManagerSV.queryProductEdit(productEditQueryReq);
		ICacheSV cacheSV = DubboConsumerFactory.getService("iCacheSV");
		SysParamSingleCond sysParamSingleCond = null;
		for (ProductEditUp productEditUp : result.getResult()) {
			// 获取类型和状态
			if (StringUtils.isNotBlank(productEditUp.getProductType())) {
				// 获取类型
				String productType = productEditUp.getProductType();
				sysParamSingleCond = new SysParamSingleCond(SysCommonConstants.COMMON_TENANT_ID,
						ComCacheConstants.TypeProduct.CODE, ComCacheConstants.TypeProduct.PROD_PRODUCT_TYPE,
						productType);
				String productTypeName = cacheSV.getSysParamSingle(sysParamSingleCond).getColumnDesc();
				productEditUp.setProductTypeName(productTypeName);
				// 获取状态
				String state = productEditUp.getState();
				sysParamSingleCond = new SysParamSingleCond(SysCommonConstants.COMMON_TENANT_ID,
						ComCacheConstants.TypeProduct.CODE, "STATE", state);
				String stateName = cacheSV.getSysParamSingle(sysParamSingleCond).getColumnDesc();
				productEditUp.setStateName(stateName);
			}
			// 产生图片地址
			if (StringUtils.isNotBlank(productEditUp.getVfsId())) {
				String attrImageSize = "80x80";
				String vfsId = productEditUp.getVfsId();
				String picType = productEditUp.getPicType();
				String imageUrl = getImageUrl(attrImageSize, vfsId, picType);
				productEditUp.setPicUrl(imageUrl);
			}
		}
		return result;
	}

	/**
	 * 获取图片地址
	 * @param attrImageSize
	 * @param vfsId
	 * @param picType
	 * @return
	 */
	private String getImageUrl(String attrImageSize, String vfsId, String picType) {
		IImageClient imageClient = IDPSClientFactory.getImageClient(SysCommonConstants.ProductImage.IDPSNS);
		if (StringUtils.isBlank(picType))
			picType = ".jpg";
		if (!picType.startsWith("."))
			picType = "." + picType;
		String imageUrl = imageClient.getImageUrl(vfsId, picType, attrImageSize);
		return imageUrl;
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
			catQuery.setTenantId("SLP");
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
	 * 点击查询按钮调用方法-获取待编辑商品
	 * @return
	 */
	@RequestMapping("/getProductList")
	@ResponseBody
	public ResponseData<PageInfoResponse<ProductEditUp>> getProductList(HttpServletRequest request,ProductEditQueryReq productEditQueryReq){
		ResponseData<PageInfoResponse<ProductEditUp>> responseData = null;
		try {
			//查询条件
			queryBuilder(request, productEditQueryReq);
			// 设置状态，新增：0；未编辑1.
			List<String> stateList = new ArrayList<>();
			stateList.add("0");
			stateList.add("1");
			productEditQueryReq.setStateList(stateList);
			PageInfoResponse<ProductEditUp> result = queryProductByState(productEditQueryReq);
			responseData = new ResponseData<PageInfoResponse<ProductEditUp>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",
					result);
		} catch (Exception e) {
			responseData = new ResponseData<PageInfoResponse<ProductEditUp>>(ResponseData.AJAX_STATUS_FAILURE,
					"获取信息异常");
			LOG.error("获取信息出错：", e);
		}
		return responseData;
	}
	
	/**
	 * 点击查询按钮调用方法-查询在售商品
	 * @return
	 */
	@RequestMapping("/getInsaleList")
	@ResponseBody
	private ResponseData<PageInfoResponse<ProductEditUp>> queryinsaleProduct(HttpServletRequest request,ProductEditQueryReq productEditQueryReq) {
		ResponseData<PageInfoResponse<ProductEditUp>> responseData = null;
		try {
			productEditQueryReq.setTenantId("SLP");
			productEditQueryReq.setProductCatId(request.getParameter("productCatId"));
			// 设置商品状态为新增和未编辑
			List<String> stateList = new ArrayList<>();
			stateList.add("5");
			productEditQueryReq.setStateList(stateList);
			PageInfoResponse<ProductEditUp> result = queryProductByState(productEditQueryReq);
			responseData = new ResponseData<PageInfoResponse<ProductEditUp>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",
					result);
		} catch (Exception e) {
			responseData = new ResponseData<PageInfoResponse<ProductEditUp>>(ResponseData.AJAX_STATUS_FAILURE,
					"获取信息异常");
			LOG.error("获取信息出错：", e);
		}
		return responseData;
	}

	/**
	 * 查询条件检查设置
	 * @param request
	 * @param productEditQueryReq
	 */
	private void queryBuilder(HttpServletRequest request, ProductEditQueryReq productEditQueryReq) {
		productEditQueryReq.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
		productEditQueryReq.setProductCatId(request.getParameter("productCatId"));
		if(!request.getParameter("productType").isEmpty())
			productEditQueryReq.setProductType(request.getParameter("productType"));
		if(!request.getParameter("productId").isEmpty())
			productEditQueryReq.setProdId(request.getParameter("productId"));
		if(!request.getParameter("productName").isEmpty())
			productEditQueryReq.setProdName(request.getParameter("productName"));
	}
	/**
	 * 点击查询按钮调用方法-获取待上架商品
	 * @return
	 */
	@RequestMapping("/getStayUpList")
	@ResponseBody
	public ResponseData<PageInfoResponse<ProductEditUp>> getStayUpProduct(HttpServletRequest request,ProductEditQueryReq productEditQueryReq){
		ResponseData<PageInfoResponse<ProductEditUp>> responseData = null;
		try {
			//查询条件
			queryBuilder(request, productEditQueryReq);
			// 设置状态，6仓库中（审核通过、手动下架放入）.
			List<String> stateList = new ArrayList<>();
			stateList.add("6");
			productEditQueryReq.setStateList(stateList);
			PageInfoResponse<ProductEditUp> result = queryProductByState(productEditQueryReq);
			responseData = new ResponseData<PageInfoResponse<ProductEditUp>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",
					result);
		} catch (Exception e) {
			responseData = new ResponseData<PageInfoResponse<ProductEditUp>>(ResponseData.AJAX_STATUS_FAILURE,
					"获取信息异常");
			LOG.error("获取信息出错：", e);
		}
		return responseData;
	}
	/**
	 * 点击查询按钮调用方法-获取售罄下架商品
	 * @return
	 */
	@RequestMapping("/getSaleDownList")
	@ResponseBody
	public ResponseData<PageInfoResponse<ProductEditUp>> getSaleDownProduct(HttpServletRequest request,ProductEditQueryReq productEditQueryReq){
		ResponseData<PageInfoResponse<ProductEditUp>> responseData = null;
		try {
			//查询条件
			queryBuilder(request, productEditQueryReq);
			// 设置状态，61售罄下架.
			List<String> stateList = new ArrayList<>();
			stateList.add("61");
			productEditQueryReq.setStateList(stateList);
			PageInfoResponse<ProductEditUp> result = queryProductByState(productEditQueryReq);
			responseData = new ResponseData<PageInfoResponse<ProductEditUp>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",
					result);
		} catch (Exception e) {
			responseData = new ResponseData<PageInfoResponse<ProductEditUp>>(ResponseData.AJAX_STATUS_FAILURE,
					"获取信息异常");
			LOG.error("获取信息出错：", e);
		}
		return responseData;
	}
	/**
	 * 点击查询按钮调用方法-获取库存暂停商品
	 * @return
	 */
	@RequestMapping("/getStorStopList")
	@ResponseBody
	public ResponseData<PageInfoResponse<ProductEditUp>> getStorStopProduct(HttpServletRequest request,ProductEditQueryReq productEditQueryReq){
		ResponseData<PageInfoResponse<ProductEditUp>> responseData = null;
		try {
			//查询条件
			queryBuilder(request, productEditQueryReq);
			// 设置状态，62停用下架.
			List<String> stateList = new ArrayList<>();
			stateList.add("62");
			productEditQueryReq.setStateList(stateList);
			PageInfoResponse<ProductEditUp> result = queryProductByState(productEditQueryReq);
			responseData = new ResponseData<PageInfoResponse<ProductEditUp>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",
					result);
		} catch (Exception e) {
			responseData = new ResponseData<PageInfoResponse<ProductEditUp>>(ResponseData.AJAX_STATUS_FAILURE,
					"获取信息异常");
			LOG.error("获取信息出错：", e);
		}
		return responseData;
	}
	
	/**
	 * 待上架商品上架
	 */
	@RequestMapping("/prodToSale")
	@ResponseBody
	public ResponseData<String> prodToInSale(@RequestParam String productId,HttpSession session){
		ResponseData<String> responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "添加成功");
		IProductManagerSV productManagerSV = DubboConsumerFactory.getService(IProductManagerSV.class);
		//封装参数进行上架操作
		ProductInfoQuery productInfoQuery = new ProductInfoQuery();
		productInfoQuery.setTenantId(SysCommonConstants.COMMON_TENANT_ID);
		productInfoQuery.setOperId(AdminUtil.getAdminId(session));
		productInfoQuery.setProductId(productId);
		BaseResponse baseResponse = productManagerSV.changeToInSale(productInfoQuery);
		LOG.debug("上架返回信息:"+JSonUtil.toJSon(baseResponse));
		ResponseHeader header = baseResponse.getResponseHeader();
		//上架出错
        if (header!=null && !header.isSuccess()){
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "添加失败:"+header.getResultMessage());
        }
		return responseData;
	}
	
}

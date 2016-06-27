package com.ai.slp.operate.web.controller.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.PageInfoResponse;
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
import com.ai.slp.operate.web.vo.ProdQueryCatVo;
import com.ai.slp.product.api.product.interfaces.IProductManagerSV;
import com.ai.slp.product.api.product.param.ProductEditQueryReq;
import com.ai.slp.product.api.product.param.ProductEditUp;
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
	private static final Logger LOG = Logger.getLogger(ProdQueryController.class);

	/**
	 * 进入页面调用-加载类目
	 */
	@RequestMapping("/add")
	public String editQuery(Model uiModel) {
		IProductCatSV productCatSV = DubboConsumerFactory.getService("iProductCatSV");
		ProductCatQuery catQuery = new ProductCatQuery();
		catQuery.setTenantId("SLP");
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
		return "product/addlist";
	}

	/**
	 * 首次打开页面时查询商品待编辑状态－默认显示第一页
	 * 
	 * @param productEditParam
	 * @return
	 */
	@RequestMapping("/getList")
	@ResponseBody
	private ResponseData<PageInfoResponse<ProductEditUp>> queryProductEdit(HttpServletRequest request) {
		ResponseData<PageInfoResponse<ProductEditUp>> responseData = null;
		try {
			// HttpSession session = request.getSession();
			// SSOClientUser user = (SSOClientUser)
			// session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
			ProductEditQueryReq productEditQueryReq = new ProductEditQueryReq();
			productEditQueryReq.setTenantId("SLP");
			productEditQueryReq.setProductCatId(request.getParameter("productCatId"));
			// 设置商品状态为新增和未编辑
			List<String> stateList = new ArrayList<>();
			// 设置状态，新增：0；未编辑1.
			stateList.add("0");
			stateList.add("1");
			productEditQueryReq.setStateList(stateList);
			PageInfoResponse<ProductEditUp> result = queryEditProduct(productEditQueryReq);
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
	 * 查询未编辑商品
	 * 
	 * @param productEditQueryReq
	 * @return
	 */
	private PageInfoResponse<ProductEditUp> queryEditProduct(ProductEditQueryReq productEditQueryReq) {
		IProductManagerSV productManagerSV = DubboConsumerFactory.getService("iProductManagerSV");
		PageInfoResponse<ProductEditUp> result = productManagerSV.queryProductEdit(productEditQueryReq);
		IImageClient imageClient = IDPSClientFactory.getImageClient(SysCommonConstants.ProductImage.IDPSNS);
		String attrImageSize = "80x80";
		ICacheSV cacheSV = DubboConsumerFactory.getService(ICacheSV.class);
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
				String vfsId = productEditUp.getVfsId();
				String picType = productEditUp.getPicType();
				if (StringUtils.isBlank(picType))
					picType = ".jpg";
				if (!picType.startsWith("."))
					picType = "." + picType;
				String imageUrl = imageClient.getImageUrl(vfsId, picType, attrImageSize);
				productEditUp.setPicUrl(imageUrl);
			}
		}
		return result;
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
			productCatUniqueReq.setTenantId("SLP");
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
	 * 点击查询按钮调用方法
	 * @return
	 */
	@RequestMapping("/getProductList")
	@ResponseBody
	public ResponseData<PageInfoResponse<ProductEditUp>> getProductList(HttpServletRequest request){
		ResponseData<PageInfoResponse<ProductEditUp>> responseData = null;
		try {
			ProductEditQueryReq productEditQueryReq = new ProductEditQueryReq();
			productEditQueryReq.setTenantId("SLP");
			productEditQueryReq.setProductCatId(request.getParameter("productCatId"));
			if(request.getParameter("productType")!=null)
				productEditQueryReq.setProductType(request.getParameter("productType"));
			if(request.getParameter("productId")!=null)
				productEditQueryReq.setProdId(request.getParameter("productId"));
			if(request.getParameter("productName")!=null)
				productEditQueryReq.setProdName(request.getParameter("productName"));
			// 设置商品状态为新增和未编辑
			List<String> stateList = new ArrayList<>();
			// 设置状态，新增：0；未编辑1.
			stateList.add("0");
			stateList.add("1");
			productEditQueryReq.setStateList(stateList);
			PageInfoResponse<ProductEditUp> result = queryEditProduct(productEditQueryReq);
			responseData = new ResponseData<PageInfoResponse<ProductEditUp>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",
					result);
		} catch (Exception e) {
			responseData = new ResponseData<PageInfoResponse<ProductEditUp>>(ResponseData.AJAX_STATUS_FAILURE,
					"获取信息异常");
			LOG.error("获取信息出错：", e);
		}
		return responseData;
	}
}

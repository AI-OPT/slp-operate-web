package com.ai.slp.operate.web.controller.product;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.opt.base.vo.PageInfoResponse;
import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.common.api.cache.param.SysParamSingleCond;
import com.ai.slp.operate.web.constants.ComCacheConstants;
import com.ai.slp.operate.web.constants.SysCommonConstants;
import com.ai.slp.product.api.product.interfaces.IProductManagerSV;
import com.ai.slp.product.api.product.param.ProductEditQueryReq;
import com.ai.slp.product.api.product.param.ProductEditUp;
import com.ai.slp.product.api.productcat.interfaces.IProductCatSV;
import com.ai.slp.product.api.productcat.param.ProdCatInfo;
import com.ai.slp.product.api.productcat.param.ProductCatQuery;

/**
 * 商城商品管理查询 Created by jackieliu on 16/6/16.
 */
@Controller
@RequestMapping("/prodquery")
public class ProdQueryController {
	private static final Logger LOG = Logger.getLogger(ProdQueryController.class);

	/**
	 * 类目思路： 后续完善－注意：每次点击下拉切换类目都要清空本级类目后的所有下拉框从新赋值显示，避免出现多余的下拉框。 显示商品思路：
	 * 根据最后显示的叶子类目查询待编辑商品信息（状态待查询）并显示在下面的分页显示内容中 分页：参照龙哥代码
	 */
	@RequestMapping("/add")
	public String editQuery(Model uiModel) {
		IProductCatSV productCatSV = DubboConsumerFactory.getService("iProductCatSV");
		ProductCatQuery catQuery = new ProductCatQuery();
		catQuery.setTenantId("SLP");
		// 查询根目录
		List<ProdCatInfo> productCatInfos = productCatSV.queryCatByNameOrFirst(catQuery);
		// 定义count用于记录类目的级数
		int count = 1;
		uiModel.addAttribute("catInfos", productCatInfos);
		// 如果有子类目,就通过父类目id查询子类目
		List<ProdCatInfo> productCatInfoList = null;
		if (productCatInfos.get(0).getIsChild().equals("Y")) {
			count++;
			List<List<ProdCatInfo>> prodInfoList = new ArrayList<>();
			// 循环查询下级目录，直到没有子类目为止
			do {
				if (count == 2) {
					catQuery.setParentProductCatId(productCatInfos.get(0).getProductCatId());
				} else {
					catQuery.setParentProductCatId(productCatInfoList.get(0).getProductCatId());
				}
				productCatInfoList = productCatSV.queryCatByNameOrFirst(catQuery);
				prodInfoList.add(productCatInfoList);
				count++;
			} while (productCatInfoList.get(0).getIsChild().equals("N"));
			uiModel.addAttribute("prodInfoList", prodInfoList);
			uiModel.addAttribute("count", --count);
		}
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
			IProductManagerSV productManagerSV = DubboConsumerFactory.getService("iProductManagerSV");
			ProductEditQueryReq productEditQueryReq = new ProductEditQueryReq();
			productEditQueryReq.setTenantId("SLP");
			// productEditQueryReq.setProductCatId(request.getParameter("productCatId"));
			productEditQueryReq.setProductCatId("1");
			// 设置商品状态为新增和未编辑
			List<String> stateList = new ArrayList<>();
			// 设置状态，新增：0；未编辑1.
			stateList.add("0");
			stateList.add("1");
			productEditQueryReq.setStateList(stateList);
			PageInfoResponse<ProductEditUp> result = productManagerSV.queryProductEdit(productEditQueryReq);
			IImageClient imageClient = IDPSClientFactory.getImageClient(SysCommonConstants.ProductImage.IDPSNS);
			String attrImageSize = "80x80";
			ICacheSV cacheSV = DubboConsumerFactory.getService(ICacheSV.class);
			for (ProductEditUp productEditUp : result.getResult()) {
				// 获取类型
				if (StringUtils.isNotBlank(productEditUp.getProductType())) {
					String productType = productEditUp.getProductType();
					SysParamSingleCond sysParamSingleCond = new SysParamSingleCond(SysCommonConstants.COMMON_TENANT_ID,
							ComCacheConstants.TypeProduct.CODE, ComCacheConstants.TypeProduct.PROD_PRODUCT_TYPE,
							productType);
					String productTypeName = cacheSV.getSysParamSingle(sysParamSingleCond).getColumnDesc();
					productEditUp.setProductTypeName(productTypeName);
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

package com.ai.slp.operate.web.controller.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.operate.web.constants.ProductCatConstants;
import com.ai.slp.product.api.productcat.interfaces.IProductCatSV;
import com.ai.slp.product.api.productcat.param.ProdCatInfo;
import com.ai.slp.product.api.productcat.param.ProductCatQuery;

/**
 * 商城商品管理查询 Created by lipeng16 on 16/7/6.
 */
@Controller
@RequestMapping("/storprodquery")
public class StorProdQueryController {
	private static final Logger LOG = LoggerFactory.getLogger(StorProdQueryController.class);
	/**
	 * 进入页面调用-加载类目
	 */
	@RequestMapping("/add")
	public String editQuery(Model uiModel) {
//		IProductCatSV productCatSV = DubboConsumerFactory.getService("iProductCatSV");
//		ProductCatQuery catQuery = new ProductCatQuery();
//		catQuery.setTenantId("SLP");
//		Map<Short, List<ProdCatInfo>> productCatMap = new HashMap<>();
//		ProdCatInfo prodCatInfo = null;
//		do {
//			// 查询同一级的类目信息
//			List<ProdCatInfo> productCatInfos = productCatSV.queryCatByNameOrFirst(catQuery);
//			prodCatInfo = productCatInfos.get(0);
//			// 把类目信息按照类目等级放入集合
//			productCatMap.put(prodCatInfo.getCatLevel(), productCatInfos);
//			catQuery.setParentProductCatId(prodCatInfo.getProductCatId());
//		} while (prodCatInfo.getIsChild().equals(ProductCatConstants.ProductCat.IsChild.HAS_CHILD));
//		uiModel.addAttribute("count", productCatMap.size() - 1);
//		uiModel.addAttribute("catInfoMap", productCatMap);
		return "product/storageProduct";
	}
	
}

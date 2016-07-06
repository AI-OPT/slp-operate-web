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
	
}

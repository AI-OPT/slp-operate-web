package com.ai.slp.operate.web.controller.product;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.product.api.productcat.interfaces.IProductCatSV;
import com.ai.slp.product.api.productcat.param.ProdCatInfo;
import com.ai.slp.product.api.productcat.param.ProductCatQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商城商品管理查询
 * Created by jackieliu on 16/6/16.
 */
@Controller
@RequestMapping("/prodquery")
public class ProdQueryController {

    @RequestMapping("/add")
    @ResponseBody
    public String editQuery(Model uiModel){
        IProductCatSV productCatSV = DubboConsumerFactory.getService("iProductCatSV");
        ProductCatQuery catQuery = new ProductCatQuery();
        catQuery.setTenantId("SLP");
        List<ProdCatInfo> productCatInfos = productCatSV.queryCatByNameOrFirst(catQuery);
        uiModel.addAttribute("catInfos",productCatInfos);
        return "product/addlist";
    }
    
}

package com.ai.slp.operate.web.controller.normproduct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.operate.web.controller.product.ProdEditController;
import com.ai.slp.product.api.normproduct.interfaces.INormProductSV;
import com.ai.slp.product.api.product.interfaces.IProductManagerSV;
import com.ai.slp.product.api.product.interfaces.IProductSV;
import com.ai.slp.product.api.productcat.interfaces.IProductCatSV;

/**
 * 对标准品进行操作
 * @author jiawen
 *
 */
@Controller
@RequestMapping("/normprodedit")
public class NormProdEditController {
	private static Logger logger = LoggerFactory.getLogger(NormProdEditController.class);
	IProductManagerSV productManagerSV;
    IProductSV productSV;
    ICacheSV cacheSV;
    INormProductSV normProductSV;
    IProductCatSV productCatSV;
    public void initConsumer() {
        if (productManagerSV == null)
            productManagerSV = DubboConsumerFactory.getService(IProductManagerSV.class);
        if (productSV == null)
            productSV = DubboConsumerFactory.getService(IProductSV.class);
        if (cacheSV == null)
            cacheSV = DubboConsumerFactory.getService(ICacheSV.class);
        if (normProductSV == null)
            normProductSV = DubboConsumerFactory.getService(INormProductSV.class);
        if (productCatSV == null)
            productCatSV = DubboConsumerFactory.getService(IProductCatSV.class);
    }
    
    /**
     * 跳转到新增页面
     * @param normprodId
     * @return
     */
    @RequestMapping("/add")
    public String addView(){
    	return "normproduct/add";
    }
    
}

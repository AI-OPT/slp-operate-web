package com.ai.slp.operate.web.controller.normproduct;

import com.ai.slp.operate.web.service.ProdCatService;
import com.ai.slp.product.api.productcat.param.ProdCatInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 对标准品进行操作
 * @author jiawen
 *
 */
@Controller
@RequestMapping("/normprodedit")
public class NormProdEditController {
	
	private static final Logger LOG = LoggerFactory.getLogger(NormProdEditController.class);
	@Autowired
	private ProdCatService prodCatService;
    
    /**
     * 跳转到新增页面
     * @param uiModel
     * @return
     */
    @RequestMapping("/add")
    public String addView(Model uiModel){
		Map<Short, List<ProdCatInfo>> productCatMap = prodCatService.loadCat();
		uiModel.addAttribute("count", productCatMap.size() - 1);
		uiModel.addAttribute("catInfoMap", productCatMap);
    	return "normproduct/add";
    }
    
    /**
     * 显示添加页面
     * @return
     */
    @RequestMapping("/addinfo")
    public String addinfoView(){
    	return "normproduct/addinfo";
    }
    
}

package com.ai.slp.operate.web.controller.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ai.opt.base.vo.PageInfoResponse;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.product.api.product.interfaces.IProductManagerSV;
import com.ai.slp.product.api.product.param.ProductEditQueryReq;
import com.ai.slp.product.api.product.param.ProductEditUp;
import com.ai.slp.product.api.productcat.interfaces.IProductCatSV;
import com.ai.slp.product.api.productcat.param.ProdCatInfo;
import com.ai.slp.product.api.productcat.param.ProductCatQuery;

/**
 * 商城商品管理查询
 * Created by jackieliu on 16/6/16.
 */
@Controller
@RequestMapping("/prodquery")
public class ProdQueryController {

	/**类目思路：
	 * 后续完善－注意：每次点击下拉切换类目都要清空本级类目后的所有下拉框从新赋值显示，避免出现多余的下拉框。
	 * 显示商品思路：
	 * 根据最后显示的叶子类目查询待编辑商品信息（状态待查询）并显示在下面的分页显示内容中
	 * 分页：参照龙哥代码
	 */
    @RequestMapping("/add")
    public String editQuery(Model uiModel){
        IProductCatSV productCatSV = DubboConsumerFactory.getService("iProductCatSV");
        ProductCatQuery catQuery = new ProductCatQuery();
        catQuery.setTenantId("SLP");
        //查询根目录
        List<ProdCatInfo> productCatInfos = productCatSV.queryCatByNameOrFirst(catQuery);
        uiModel.addAttribute("catInfos",productCatInfos);
        //如果有子类目,就通过父类目id查询子类目
        List<ProdCatInfo> productCatInfoList = null;
        if(productCatInfos.get(0).getIsChild().equals("Y")){
        	int count = 2;
        	//循环查询下级目录，直到没有子类目为止
	        do{
	        	if(count==2){
	        		catQuery.setParentProductCatId(productCatInfos.get(0).getProductCatId());
	        	}else{
	        		catQuery.setParentProductCatId(productCatInfoList.get(0).getProductCatId());
	        	}
				productCatInfoList = productCatSV.queryCatByNameOrFirst(catQuery);
				uiModel.addAttribute("catInfos"+count,productCatInfoList);
				count++;
	        }while(productCatInfoList.get(0).getIsChild().equals("N"));
        }
        //获取叶子节点的类目id
        String productCatId = productCatInfoList.get(0).getProductCatId();
//        ProductEditQueryReq productEditQueryReq = new ProductEditQueryReq();
//        productEditQueryReq.setTenantId("SLP");
//        productEditQueryReq.setProductCatId(productCatId);
//        查询商品待编辑状态
        PageInfoResponse<ProductEditUp> productEditUpPage = queryProductEdit("SLP",productCatId);
        uiModel.addAttribute("productEditInfo",productEditUpPage);
        //通过查询条件查询商品编辑信息
        return "product/addlist";
    }
    
    
    /**
     * 首次打开页面时查询商品待编辑状态－默认显示第一页
     * 
     * @param productEditParam
     * @return
     */
    private PageInfoResponse<ProductEditUp> queryProductEdit(String tenantId,String productCatId){
    	IProductManagerSV productManagerSV = DubboConsumerFactory.getService("iProductManagerSV");
    	PageInfoResponse<ProductEditUp> pageInfo = new PageInfoResponse<>();
    	ProductEditQueryReq productEditQueryReq = new ProductEditQueryReq();
	    productEditQueryReq.setTenantId("SLP");
	    productEditQueryReq.setProductCatId(productCatId);
	    //设置商品状态为新增和未编辑
	    List<String> stateList = new ArrayList<>();
	    stateList.add("0");
        stateList.add("1");
        productEditQueryReq.setStateList(stateList);
	    pageInfo = productManagerSV.queryProductEdit(productEditQueryReq);
    	return pageInfo;
    }
    
}

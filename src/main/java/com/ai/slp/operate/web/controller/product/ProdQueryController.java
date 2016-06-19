package com.ai.slp.operate.web.controller.product;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.product.api.productcat.interfaces.IProductCatSV;
import com.ai.slp.product.api.productcat.param.ProdCatInfo;
import com.ai.slp.product.api.productcat.param.ProductCatQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 商城商品管理查询
 * Created by jackieliu on 16/6/16.
 */
@Controller
@RequestMapping("/prodquery")
public class ProdQueryController {

	/**类目思路：
	 * 1.查询出所有的根目录并显示在下拉菜单中－－此处类目等级为一级且状态有效的接口
	 * 2.根据父类目显示的类目(即第一个类目)查询下一级类目的集合并显示在下拉菜单中
	 * 3.继续查询下一级类目直到类目没有子目录。－－此处需要一个通过父类目id查询子类目的接口。
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
        //获取根目录的第一个
        ProdCatInfo prodCatInfo ＝ productCatInfos.get(0);
		//判断当前类目是否有子分类
		String isChild = prodCatInfo.getIsChild();
		//如果有子分类
		if(){
			String productCatId = prodCatInfo.getProductCatId();
			//通过父类目id查询子类目
		}
        		
        return "product/addlist";
    }
    
}

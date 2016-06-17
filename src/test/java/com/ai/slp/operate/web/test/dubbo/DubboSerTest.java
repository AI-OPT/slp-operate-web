package com.ai.slp.operate.web.test.dubbo;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.product.api.productcat.interfaces.IProductCatSV;
import com.ai.slp.product.api.productcat.param.ProductCatQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jackieliu on 16/6/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:context/*.xml"})
public class DubboSerTest {

    @Test
    public void testIProductCatSV() {
        IProductCatSV productCatSV = DubboConsumerFactory.getService("iProductCatSV");
        ProductCatQuery catQuery = new ProductCatQuery();
//        ListForRes<ProdCatInfo> catInfoList = productCatSV.queryCatByNameOrFirst(catQuery);
//        for (ProdCatInfo catInfo:catInfoList.getObjList()){
//            System.out.println(catInfo.getProductCatName()+" ");
//        }
    }
}

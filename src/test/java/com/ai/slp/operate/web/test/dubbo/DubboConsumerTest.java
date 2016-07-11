package com.ai.slp.operate.web.test.dubbo;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.product.api.product.interfaces.IProductSV;
import com.ai.slp.product.api.product.param.ProductInfo;
import com.ai.slp.product.api.product.param.ProductInfoQuery;
import org.junit.Test;

/**
 * Created by jackieliu on 16/7/8.
 */
public class DubboConsumerTest {
    
    @Test
    public void queryProductByIdTest(){
        IProductSV productSV = DubboConsumerFactory.getService(IProductSV.class);
        ProductInfoQuery infoQuery = new ProductInfoQuery();
        infoQuery.setTenantId("SLP");
        infoQuery.setProductId("1000000000000001");
        ProductInfo productInfo = productSV.queryProductById(infoQuery);
        System.out.println(productInfo.getState());
    }
}

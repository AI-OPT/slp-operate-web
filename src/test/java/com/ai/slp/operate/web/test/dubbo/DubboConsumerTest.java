package com.ai.slp.operate.web.test.dubbo;

import com.ai.opt.base.vo.PageInfoResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.product.api.normproduct.interfaces.INormProductSV;
import com.ai.slp.product.api.normproduct.param.NormProdRequest;
import com.ai.slp.product.api.normproduct.param.NormProdResponse;
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

    @Test
    public void queryNormProdTest(){
        INormProductSV normProductSV = DubboConsumerFactory.getService(INormProductSV.class);
        NormProdRequest request = new NormProdRequest();
        request.setTenantId("SLP");
        request.setPageSize(30);
        request.setPageNo(1);
        request.setProductCatId("10000010010000");
        PageInfoResponse<NormProdResponse> response = normProductSV.queryNormProduct(request);
        ResponseHeader header = response.getResponseHeader();
        if (header!=null){
            System.out.println(header.isSuccess()+","+header.getResultCode());
        }else
            System.out.println("Head is null");
    }
}

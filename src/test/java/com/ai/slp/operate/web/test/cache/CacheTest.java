package com.ai.slp.operate.web.test.cache;

import java.util.List;

import org.junit.Test;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.common.api.cache.param.SysParam;
import com.ai.slp.common.api.cache.param.SysParamMultiCond;
import com.alibaba.fastjson.JSON;

public class CacheTest {
	@Test
	public void testCache(){
		ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
		SysParamMultiCond payQParams = new SysParamMultiCond("SLP", "ORD_OD_FEE_TOTAL", "PAY_STYLE");
		List<SysParam> payStyleParamList = iCacheSV.getSysParamList(payQParams);
		System.out.println("result="+JSON.toJSONString(payStyleParamList));
	}
}

package com.ai.slp.operate.web.test.mcs;

import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class mcsTest {
    private ICacheClient cacheClient;

    private String namespace = "com.ai.opt.uni.session.sessionclient.baasopweb";

    @Before
    public void initData() {
        this.cacheClient = MCSClientFactory.getCacheClient(
                namespace);
    }
    //@Ignore
    @Test
    public void addCache() {
        cacheClient.set("testKey", "testValue");
        assertEquals("testValue", cacheClient.get("testKey"));
    }
}

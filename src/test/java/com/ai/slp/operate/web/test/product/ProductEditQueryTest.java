package com.ai.slp.operate.web.test.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.slp.operate.web.controller.product.ProdQueryController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:context/*.xml"})
public class ProductEditQueryTest {
	
	@Test
	public void editQueryTest(){
		ProdQueryController ProdQueryController = new ProdQueryController();
		ProdQueryController.editQuery(null);
	}

	@Test
	public void getCatInfo(){
		
	}
}

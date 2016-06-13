package com.ai.slp.operate.web.controller.account;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PayController {
	//

    private static final Logger LOG = Logger.getLogger(PayController.class);
	private static final String ACCOUNT_ID = "1111";
	private static final String TENANT_ID = "1111";
	//
	
	@RequestMapping("/recharge/one")
	public ModelAndView one(HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("linkModel", "accountBalance");
		paramMap.put("testName", "zhangzd");
        ModelAndView view = new ModelAndView("jsp/account/recharge/one",paramMap);
        return view;
    }
	@RequestMapping("/recharge/two")
	public ModelAndView two(HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("linkModel", "accountBalance");
		paramMap.put("testName", "zhangzd");
        ModelAndView view = new ModelAndView("jsp/account/recharge/two",paramMap);
        return view;
    }
	@RequestMapping("/recharge/three")
	public ModelAndView three(HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("linkModel", "accountBalance");
		paramMap.put("testName", "zhangzd");
        ModelAndView view = new ModelAndView("jsp/account/recharge/three",paramMap);
        return view;
    }
	@RequestMapping("/recharge/four")
	public ModelAndView four(HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("linkModel", "accountBalance");
		paramMap.put("testName", "zhangzd");
        ModelAndView view = new ModelAndView("jsp/account/recharge/four",paramMap);
        return view;
    }
}

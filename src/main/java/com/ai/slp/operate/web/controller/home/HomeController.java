package com.ai.slp.operate.web.controller.home;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/home")
@RestController
public class HomeController {

	private static final Logger LOGGER = Logger.getLogger(HomeController.class);

	@RequestMapping({"","/"})
	public ModelAndView index(HttpServletRequest request) {
		return new ModelAndView("jsp/home/index");
	}

}

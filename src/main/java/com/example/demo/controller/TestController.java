package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class TestController {
	
	
	
	ModelAndView model = new ModelAndView();
	
	//For testing
		@RequestMapping(value = {"/test"},method=RequestMethod.GET)
		public ModelAndView testing() {
			try {
				String message = "this message is being sent from within the test method";
				
//				log.info("Finding user: -> {}",repo.findById(1000));
				
				model.setViewName("");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return model;
		}
}
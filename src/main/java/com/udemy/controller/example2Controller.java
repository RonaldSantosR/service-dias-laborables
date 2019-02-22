package com.udemy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller

@RequestMapping("/say")
public class example2Controller {
	
	public static final String example2view = "example2";
	
	
	@GetMapping("/request1")
	public ModelAndView request(@RequestParam(name="nm", required=false, defaultValue="NULL") String name) {
		
		ModelAndView mav = new ModelAndView(example2view);
		mav.addObject("nm_model",name);
		return mav;
	}
	/*
	@GetMapping("/request2/{nm}")
	public ModelAndView request2(@PathVariable("nm") String name) {
		ModelAndView mav = new ModelAndView(example2view);
		mav.addObject("nm_model",name);
		return mav;
	}
	*/
}

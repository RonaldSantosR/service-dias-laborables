package com.udemy.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.udemy.service.exampleservice;
import com.udemy.model.person;

@Controller 
@RequestMapping("/example")
public class exampleController {

	public static final String EXAMPLE_view="example";
	
	@Autowired
	@Qualifier("exampleService")
	private exampleservice exampleservice;
	
	@Autowired
	@Qualifier("examplecomponent")
	private com.udemy.component.examplecomponent examplecomponent;
	//primera forma
	@GetMapping("/examplestring")	
	public String exampleString(Model model) {
		examplecomponent.sayhello();
		model.addAttribute("people",exampleservice.getListPeople());
		return EXAMPLE_view;
	}
	
	//segunda forma
	@GetMapping("/exampleMAV")
	public ModelAndView exampleMAV() {
		ModelAndView mav = new ModelAndView(EXAMPLE_view);
		mav.addObject("people",exampleservice.getListPeople());
		return mav;
	}
	

	
}

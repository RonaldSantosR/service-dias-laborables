package com.udemy.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.udemy.model.person;

@Controller


@RequestMapping("/example3")
public class example3 {
	
	public static final String form_view = "form";
	public static final String form_result = "result";

	@GetMapping("/")
	public String Redict() {
		return "redirect:/example3/form";
	}	
	
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("person", new person());
		return form_view;
	}
	

	
	
	@PostMapping("/addperson")
	public ModelAndView addperson(@Valid @ModelAttribute("person") person person, BindingResult bindinresult) {
		ModelAndView mav = new ModelAndView(form_result);
		if(bindinresult.hasErrors()) {
			mav.setViewName(form_result);

		}else {
			mav.setViewName(form_result);
			mav.addObject("person",person);
		}

		return mav;
	}
	
}

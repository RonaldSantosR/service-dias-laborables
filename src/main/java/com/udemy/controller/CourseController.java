package com.udemy.controller;
import com.udemy.entity.course;
import com.udemy.service.impl.*;
import com.udemy.service.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/courses")
public class CourseController {
	private static final String coursesview = "courses";
	
	private static final Log LOG = LogFactory.getLog(CourseController.class);
	
	@Autowired
	@Qualifier("CourseService")
	private CourseService courseservice;
	
	@GetMapping("/listcourses")
	public ModelAndView Listaallcourses() {
		LOG.info("call: ---Listallcourses()");
		ModelAndView mav = new ModelAndView(coursesview);
		mav.addObject("course", new course());
		mav.addObject("courses", courseservice.listAllCourses());
		return mav;
	}
	
	@PostMapping("/addcourses")
	public String addCourses(@ModelAttribute("course") course course) {
		LOG.info("call: ---addCourses()" + "--param:--"+course.toString());
		courseservice.addcourse(course);
		return "redirect:/courses/listcourses";
	}
	
}

package com.udemy.converter;

import org.springframework.stereotype.Component;

import com.udemy.entity.course;
import com.udemy.model.CourseModel;

@Component("courseConverter")
public class CourseConverter {
	
	//Entity==> MODEL
	public CourseModel entity2model(course course) {
		CourseModel coursemodel = new CourseModel();
		coursemodel.setName(course.getNombre());
		coursemodel.setDescripcion(course.getDescription());
		coursemodel.setPrice(course.getPrice());
		coursemodel.setHours(course.getHours());
		return coursemodel;
	}
	
	
	
	
	//MODEL==>Entity
	public course Model2entity(CourseModel coursemodel){
		course course = new course();
		course.setNombre(coursemodel.getName());
		course.setDescription(coursemodel.getDescripcion());
		course.setHours(coursemodel.getHours());
		course.setPrice(coursemodel.getPrice());
		return course;
	}
	
	
	
}

package com.udemy.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.udemy.entity.course;
import com.udemy.service.CourseService;
import com.udemy.repository.*;

@Service("CourseService")
public class courseservicesimpl implements CourseService{
	
	private static final Log LOG = LogFactory.getLog(courseservicesimpl.class);
	
	@Autowired
	@Qualifier("coursejparepository")
	private coursejparepository coursejparepository;
	
	
	@Override
	public List<course> listAllCourses() {
		LOG.info("call listAllcoursesservice()");
		return coursejparepository.findAll();
	}

	@Override
	public course addcourse(course course) {
		LOG.info("call addcoursecoursesservice()"+"--param--"+course.toString());
		return coursejparepository.save(course);
	}

	@Override
	public int removeCourse(int id) {
		coursejparepository.deleteById(id);
		return 0;
	}

	@Override
	public course updateCourse(course course) {
		return 	coursejparepository.save(course);

	}


}

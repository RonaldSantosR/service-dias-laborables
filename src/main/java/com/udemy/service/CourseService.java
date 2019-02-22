package com.udemy.service;

import java.util.List;

import com.udemy.entity.course;

public interface CourseService {
	
	public abstract List<course> listAllCourses();
	public abstract course addcourse(course course);
	public abstract int removeCourse(int id);
	public abstract course updateCourse(course course);

}

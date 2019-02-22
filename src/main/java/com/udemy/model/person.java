package com.udemy.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class person {
	@NotNull
	@Size(min=2,max=6)
	private String name;
	@NotNull
	@Size(min=1,max=3)
	private int age;
	
	public person(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public person() {
		
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	

}

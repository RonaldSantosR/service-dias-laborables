package com.udemy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.udemy.model.person;
import com.udemy.service.exampleservice;

@Service("exampleService")
public class ExampleServiceImpl implements exampleservice{

	@Override
	public List<person> getListPeople() {
			List<person> people = new ArrayList<>();
			people.add(new person("yohan",22));
			people.add(new person("Raul",22));
			people.add(new person("Gian Pier",23));
			people.add(new person("Jorge",24));
			return people;
	}

}

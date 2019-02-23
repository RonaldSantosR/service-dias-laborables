package com.udemy.repository;

import org.springframework.data.repository.CrudRepository;

import com.udemy.model.user;

public interface userjparepository extends CrudRepository<user,Long> {
		
		@SuppressWarnings("unchecked")
		user save(user user);
	
}

package com.udemy.service;

import java.util.List;

import com.udemy.model.user;

public interface UserService {

	user save(user user);

	List<user> findAll();

}

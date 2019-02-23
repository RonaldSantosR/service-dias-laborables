package com.udemy.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.model.user;
import com.udemy.service.UserService;
import com.udemy.util.QueryResult;
import com.udemy.util.RestResponse;
@RestController
public class UserController {
	@Autowired	
	protected UserService userservice;
	
	protected ObjectMapper mapper;
	
	@PostMapping("/saveOrupdate")
	public RestResponse saveOrupdate(@RequestBody String userJson) throws JsonParseException, JsonMappingException, IOException {
		this.mapper=new ObjectMapper();
		user user = this.mapper.readValue(userJson, user.class); 
		
		if(!this.validate(user)) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),"Los campos no estan diligenciados");
		}
		this.userservice.save(user);
		return new RestResponse(HttpStatus.OK.value(),"Operacion Exitosa");
	}
	
	@PostMapping("/deleteuser")
	public void deleteuser(@RequestBody String userJson) throws Exception {
		this.mapper=new ObjectMapper();
		user user = this.mapper.readValue(userJson, user.class); 
		
		if(user.getId() == 0) {
			throw new Exception("El id esta nulo");
		}
		
		this.userservice.deleteUser(user.getId());
	}
	
	@GetMapping("/getUsers")
	public List<user> getUsers(){
		return this.userservice.findAll();
	}
	
	
	private boolean validate(user user) {
		boolean isvaled=true;
		
		if( user.getFirstname() ==  null) {
			isvaled=false;
		}
		
		return isvaled;
	}
	
	
}

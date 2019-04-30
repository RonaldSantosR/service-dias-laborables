package com.udemy.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.udemy.service.classes.FeriadoPermanenteService;
import com.udemy.util.RestResponse;

@RestController
@RequestMapping("/feriadospermanentes") 
public class FeriadoPermanenteController{
	
	@Autowired
	private FeriadoPermanenteService feriadopermanentes;
	
	
	@DeleteMapping({"/{id}","/"})
	public RestResponse borrarpermanente(@PathVariable Map<String, String> pathVariablesMap) throws JsonParseException, JsonMappingException, IOException {		
	return feriadopermanentes.eliminar(pathVariablesMap);
	}
}

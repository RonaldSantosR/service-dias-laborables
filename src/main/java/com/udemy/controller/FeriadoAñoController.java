package com.udemy.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.udemy.service.classes.FeriadoAñoService;
import com.udemy.util.RestResponse;

@RestController
@RequestMapping("/feriadosanios") 
public class FeriadoAñoController {
	@Autowired
	private FeriadoAñoService feriadoAñoService;
	
	
	@DeleteMapping({"/{id}","/"})
	public RestResponse borraraño(@PathVariable Map<String, String> pathVariablesMap) throws JsonParseException, JsonMappingException, IOException {		
	return feriadoAñoService.eliminar(pathVariablesMap);
	}
}

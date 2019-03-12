package com.udemy.controller;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

import com.udemy.component.examplecomponent;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.entity.Ambito;
import com.udemy.entity.Dia;
import com.udemy.entity.DiaHora;
import com.udemy.entity.FeriadoPermanente;
import com.udemy.service.interfaces.IAmbitoService;
import com.udemy.util.RestResponse;

@RestController
@RequestMapping("/ambitos")
public class AmbitoController {
	
	protected ObjectMapper mapper;
	@Autowired
	private IAmbitoService ambitoservice;
	
	@PostMapping
	public RestResponse guardarambito(@RequestBody String  ambito) throws JsonParseException, JsonMappingException, IOException {
		this.mapper=new ObjectMapper();
		mapper.isEnabled(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
		Ambito ambitos = this.mapper.readValue(ambito, Ambito.class); 
		try{
			mapper.reader(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY).readTree(ambito);
			return ambitoservice.Registrar(ambitos);
			
		}catch(JsonMappingException e){
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),"Mal Formato de Json");  		
		}catch (NullPointerException e) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(),"Verifique los datos ingresados");
      }
	}
	
	
	@PutMapping("/{id}")
	public RestResponse actualizar(@PathVariable Long id,@RequestBody Ambito ambito) {			
		try{		
		ambito.setId(id);
		return ambitoservice.Actualizar(ambito,id);
		}  catch (NullPointerException e) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(),"Verifique los datos ingresados");
      }
	}
	
	
}
	



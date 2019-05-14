package com.udemy.controller;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.udemy.entity.Feriado;
import com.udemy.model.modelFeriado;
import com.udemy.service.classes.RegionService;
import com.udemy.service.classes.FeriadoPermanenteService;
import com.udemy.service.interfaces.IFeriadoService;
import com.udemy.util.RestResponse;

@RestController
@RequestMapping("/feriados") 
public class FeriadoPermanenteController{
	
	@Autowired
	private FeriadoPermanenteService feriadopermanentes;
	
	private static final Log Logger = LogFactory.getLog(FeriadoPermanenteController.class);

	
	@Autowired
	IFeriadoService feriados;
	
	@DeleteMapping({"/{id}","/"})
	public ResponseEntity<RestResponse> borrarpermanente(@PathVariable Map<String, String> pathVariablesMap) throws JsonParseException, JsonMappingException, IOException {		
		//return feriadopermanentes.eliminar(pathVariablesMap);
		RestResponse response = feriadopermanentes.eliminar(pathVariablesMap);
		Logger.info("Service1");
		if(response.getResponsecode()==HttpStatus.BAD_REQUEST.value()) {
			return new ResponseEntity<RestResponse>(response,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<RestResponse>(response,HttpStatus.OK);
	}	
	
	
	@GetMapping()
	public ResponseEntity<Iterable<modelFeriado>> listarferiados()
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return new ResponseEntity<Iterable<modelFeriado>>(feriados.listarferiados(),HttpStatus.OK);
	}
	
	@PostMapping
	public RestResponse guardarferiado(@RequestBody String feriado)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return feriados.guardarrferiado(feriado);	
	}
	
	@GetMapping("/fechas")	
	public ResponseEntity<Iterable<Date>> listarfecha(){
		return new ResponseEntity<Iterable<Date>> (feriados.listarfechas(), HttpStatus.OK);
	}
}

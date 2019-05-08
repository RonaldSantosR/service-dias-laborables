package com.udemy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.entity.SubAmbito;
import com.udemy.service.interfaces.ISubAmbitoService;
import com.udemy.util.RestResponse;

@RestController
@RequestMapping("/subambitos")
public class SubAmbitoController {

	@Autowired
	ISubAmbitoService subambitoService;
	
	@GetMapping
	public ResponseEntity<Iterable<SubAmbito>> listarAll() {
		return new ResponseEntity<Iterable<SubAmbito>>(subambitoService.listarAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public RestResponse guardar(@RequestBody String subambito) {
		
		ObjectMapper mapper = new ObjectMapper();
		SubAmbito subambitoBD= new SubAmbito();
		try {
			subambitoBD = mapper.readValue(subambito, SubAmbito.class);
			subambitoBD.setActivo(true);
			
			return subambitoService.guardar(subambitoBD);
		} catch (Exception e) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), "NOMBRE REPETIDO");

		}
	}
	
	@PutMapping("/{id}")
	public RestResponse modificar(@PathVariable Long id, @RequestBody String subambito) {
		
		ObjectMapper mapper = new ObjectMapper();
		SubAmbito subambitoBD=null;
		try {
			subambitoBD = mapper.readValue(subambito, SubAmbito.class);
			subambitoBD.setId(id);
			return subambitoService.guardar(subambitoBD);
		} catch (Exception e) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), "NOMBRE REPETIDO");
		}
	}
	
}

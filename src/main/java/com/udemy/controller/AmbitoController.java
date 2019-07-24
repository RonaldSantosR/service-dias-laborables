package com.udemy.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.entity.Ambito;
import com.udemy.service.interfaces.IAmbitoService;
import com.udemy.util.RestResponse;

@RestController
@RequestMapping("/ambitos")
public class AmbitoController {

	@Autowired
	IAmbitoService ambitoService;
	
	@GetMapping
	public ResponseEntity<Iterable<Ambito>> listarAll() {
		return new ResponseEntity<Iterable<Ambito>>(ambitoService.listarAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<RestResponse> guardar(@RequestBody String subambito) {
		
		ObjectMapper mapper = new ObjectMapper();
		Ambito subambitoBD= new Ambito();
		
		
			try {
				subambitoBD = mapper.readValue(subambito, Ambito.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			subambitoBD.setActivo(true);
			RestResponse response =	 ambitoService.guardar(subambitoBD);
			
			if(response.getResponsecode()==HttpStatus.BAD_REQUEST.value()) {
				
				return new ResponseEntity<RestResponse>(response,HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<RestResponse>(response,HttpStatus.OK);
			
	
			//return new ResponseEntity<RestResponse>(response,HttpStatus.BAD_REQUEST);

	
		
//		if(subambitoBD.getNombre()!=null) {
//			return subambitoService
//		}
//		return null;
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RestResponse> modificar(@PathVariable Long id, @RequestBody String subambito) {
		/*
		ObjectMapper mapper = new ObjectMapper();
		SubAmbito subambitoBD=null;
		try {
			subambitoBD = mapper.readValue(subambito, SubAmbito.class);
			subambitoBD.setId(id);
			return subambitoService.guardar(subambitoBD);
		} catch (Exception e) {
			return new ResponseEntity<RestResponse> ( RestResponse(), HttpStatus.OK);
			//return new RestResponse(HttpStatus.BAD_REQUEST.value(), "ERROR");
		}
	*/
		ObjectMapper mapper = new ObjectMapper();
		Ambito subambitoBD=null;
		try {
			subambitoBD = mapper.readValue(subambito, Ambito.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		subambitoBD.setId(id);
		RestResponse response =	ambitoService.actualizar(subambitoBD);
		if(response.getResponsecode()==HttpStatus.BAD_REQUEST.value()) {
			
			return new ResponseEntity<RestResponse>(response,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<RestResponse>(response,HttpStatus.OK);

	}

	@GetMapping(params="ids")
	public ResponseEntity<Iterable<Ambito>> listarByIds(@RequestParam List<Long> ids) {
		return new ResponseEntity<Iterable<Ambito>>(ambitoService.listarByIds(ids), HttpStatus.OK);
	}
	
	@GetMapping("/{id}/region")
	public ResponseEntity<Iterable<Ambito>> listarAmbitosByRegionId(@PathVariable Long id) {
		return new ResponseEntity<Iterable<Ambito>>(ambitoService.listarAmbitoByRegionId(id), HttpStatus.OK);
	}
	
	@GetMapping(params="nombres")
	public ResponseEntity<List<Long>> listarAmbitoPorNombres(@RequestParam List<String> nombres) {
		return new ResponseEntity<List<Long>>(ambitoService.listarAmbitosIdByNombre(nombres), HttpStatus.OK);
	}
	
}

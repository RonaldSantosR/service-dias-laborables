package com.udemy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.udemy.entity.TipoPeriodo;
import com.udemy.service.interfaces.IPeriodoService;

@RestController
@RequestMapping("/periodos")
public class PeriodoController {
	
	@Autowired
	IPeriodoService periodo;
	
	@GetMapping
	//ResponseEntity<Iterable<Ambito>>
	public ResponseEntity<Iterable<TipoPeriodo>> Mostrarperiodos(){
		return new ResponseEntity<Iterable<TipoPeriodo>> (periodo.listar(), HttpStatus.OK);
	}
	
	
}

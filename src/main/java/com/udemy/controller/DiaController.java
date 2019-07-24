package com.udemy.controller;
import com.udemy.entity.Dia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.service.classes.DiaService;

@RestController
@RequestMapping("/dias")
public class DiaController {
	
	@Autowired
	private DiaService diaservice;
	
	@GetMapping
	public ResponseEntity<List<Dia>> MostrarDia(){
		return new ResponseEntity<List<Dia>>(diaservice.findall(),HttpStatus.OK);
	}
	
}

package com.udemy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.entity.Dia;
import com.udemy.entity.TipoPeriodo;
import com.udemy.service.interfaces.IPeriodoService;

@RestController
@RequestMapping("/periodos")
public class PeriodoController {
	
	@Autowired
	IPeriodoService periodo;
	
	@GetMapping
	public Iterable<TipoPeriodo> Mostrarperiodos(){
		return periodo.listar();
	}
	
	
}

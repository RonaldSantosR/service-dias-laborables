package com.udemy.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.entity.Ambito;
import com.udemy.entity.FeriadoPermanente;
import com.udemy.service.classes.FeriadoPermanenteService;
import com.udemy.service.interfaces.IAmbitoService;
import com.udemy.util.RestResponse;

@RestController
@RequestMapping("/feriados")
public class FeriadoController {
	
	protected ObjectMapper mapper;

	
	@Autowired
	private FeriadoPermanenteService feriadopermanentes;
	
	@Autowired
	private IAmbitoService ambitoservice; 
	
	
	@PostMapping("/{id}/feriadospermanentess")
	public RestResponse guardarferiadopermanente(@RequestBody FeriadoPermanente feriadopermanente,@PathVariable Long id) {
 
		try{
		Ambito ambito = ambitoservice.ListarById(id);
		feriadopermanentes.registrar(feriadopermanente,ambito);
		}catch (NullPointerException e) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(),"Verifique los datos ingresados");
      }
		//ambito.setFeriado(feriadospermanentes);
		return new RestResponse(HttpStatus.OK.value(),"Operacion Exitosa");	}
}

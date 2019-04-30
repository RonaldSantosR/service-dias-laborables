package com.udemy.service.interfaces;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.udemy.entity.Ambito;
import com.udemy.entity.DiaHora;
import com.udemy.util.RestResponse;

public interface IAmbitoService {
			
	RestResponse Registrar(String ambito) throws JsonParseException, JsonMappingException, IOException, ParseException;
	RestResponse Actualizar(String ambito,Long id) throws JsonParseException, JsonMappingException, IOException, ParseException;
	Ambito ListarById(Long id); 
	Iterable<Ambito> ListarAmbitos() throws JsonProcessingException;
	Iterable<DiaHora> listarhoras(Long id);
}

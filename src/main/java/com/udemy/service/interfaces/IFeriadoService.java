package com.udemy.service.interfaces;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import com.udemy.entity.Region;
import com.udemy.entity.Feriado;
import com.udemy.model.modelFeriado;
import com.udemy.util.RestResponse;

public interface IFeriadoService {
	
	RestResponse registrar(String feriadopermanente,Map<String, String> id) throws IOException;
	
	RestResponse eliminar(Map<String, String> id);
	
	RestResponse registrarferiado(String feriado,Map<String, String> id) throws IOException;
	
	RestResponse guardarrferiado(String feriado) throws IOException;

	
	Iterable<modelFeriado> listarferiados();

	Iterable<Date> listarfechas();


}

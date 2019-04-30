package com.udemy.service.interfaces;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import com.udemy.entity.Ambito;
import com.udemy.entity.Feriado;
import com.udemy.entity.FeriadoPermanente;
import com.udemy.model.modelferiadopermanente;
import com.udemy.util.RestResponse;

public interface IFeriadoService {
	
	RestResponse registrar(String feriadopermanente,Map<String, String> id) throws IOException;
	
	RestResponse eliminar(Map<String, String> id);
	

}

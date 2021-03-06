package com.udemy.service.interfaces;

import java.text.ParseException;
import java.util.List;
import com.udemy.model.modelFeriado;

public interface IFeriadoSer {
	List<Object> Listarferiados(Long id,String fecha1,String fecha2) throws ParseException;
	
	boolean compararfechas(String fecha1,String fecha2) throws ParseException;
	
	boolean formatofecha(String fecha1, String fecha2);
	
	int validar(Long id,String fecha1,String fecha2) throws ParseException;
	
	Iterable<modelFeriado> listarferiados(Long id);

}

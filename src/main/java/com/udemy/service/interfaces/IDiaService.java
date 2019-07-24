package com.udemy.service.interfaces;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import com.udemy.entity.Dia;

public interface IDiaService {
	List<Dia> findall();
	Iterable<Dia> diaslaborales( Long id);
	
	HashMap<Integer,String> listardiaslaborales(Long id,String fecha,String fecha2) throws ParseException;
	
	
}

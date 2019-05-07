package com.udemy.service.interfaces;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.udemy.entity.Dia;

public interface IDiaService {
	List<Dia> findall();
	Iterable<Dia> diaslaborales( Long id);
	
	HashMap<Integer,String> listardiaslaborales(Long id,String fecha,String fecha2) throws ParseException;
	
	
}

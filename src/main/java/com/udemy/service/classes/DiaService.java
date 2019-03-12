package com.udemy.service.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.entity.Dia;
import com.udemy.repository.DiaRepository;
import com.udemy.service.interfaces.IDiaService;

@Service
public class DiaService implements IDiaService{

	@Autowired
	private DiaRepository diaRepository;
	
	@Override
	public List<Dia> findall() {
		return diaRepository.findAll();
	}
		
	
	
	
}

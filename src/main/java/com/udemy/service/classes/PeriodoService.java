package com.udemy.service.classes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.entity.TipoPeriodo;
import com.udemy.repository.TipoPeriodoRepository;
import com.udemy.service.interfaces.IPeriodoService;

@Service
public class PeriodoService implements IPeriodoService {
	
	@Autowired
	TipoPeriodoRepository tipope;

	@Override
	public Iterable<TipoPeriodo> listar() {
		return tipope.findAll();
	}
	
	
	
}

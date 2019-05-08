package com.udemy.service.interfaces;

import java.util.List;

import com.udemy.entity.Dia;
import com.udemy.entity.TipoPeriodo;

public interface IPeriodoService {
	Iterable<TipoPeriodo> listar();

}

package com.udemy.service.interfaces;

import com.udemy.entity.Ambito;
import com.udemy.util.RestResponse;

public interface IAmbitoService {
			
	RestResponse Registrar(Ambito ambito);
	RestResponse Actualizar(Ambito ambito,Long id);
	Ambito ListarById(Long id); 
}

package com.udemy.service.interfaces;

import java.util.List;

import com.udemy.entity.Ambito;
import com.udemy.util.RestResponse;

public interface IAmbitoService {
	
	
	public Iterable<Ambito> listarAll();
	Ambito listarById(Long id);
	RestResponse guardar(Ambito subambito);
	public Iterable<Ambito> listarSubAmbitosActivosByAmbitoId(Long id);
	RestResponse actualizar(Ambito subambito);
	Iterable<Ambito> listarByIds(List<Long> ids); 
	Iterable<Ambito> listarAmbitoByRegionId(Long regionId);
}

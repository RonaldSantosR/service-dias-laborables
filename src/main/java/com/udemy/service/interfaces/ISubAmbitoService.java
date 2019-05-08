package com.udemy.service.interfaces;

import com.udemy.entity.SubAmbito;
import com.udemy.util.RestResponse;

public interface ISubAmbitoService {
	
	
	public Iterable<SubAmbito> listarAll();
	SubAmbito listarById(Long id);
	RestResponse guardar(SubAmbito subambito);
	public Iterable<SubAmbito> listarSubAmbitosActivosByAmbitoId(Long id);


}

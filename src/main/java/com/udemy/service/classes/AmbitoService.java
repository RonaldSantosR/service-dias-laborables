package com.udemy.service.classes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.udemy.entity.Ambito;
import com.udemy.repository.IAmbitoDao;
import com.udemy.service.interfaces.IAmbitoService;
import com.udemy.util.RestResponse;


@Service
public class AmbitoService implements IAmbitoService {
	
	@Autowired
	IAmbitoDao ambitoDao;
	
	@Override
	public Iterable<Ambito> listarAll() {
		return ambitoDao.findAll();
	}

	@Override
	public Ambito listarById(Long id) {
		 Optional<Ambito> subambitobd =	ambitoDao.findById(id);
		 if(subambitobd.isPresent()) {
			 Ambito subambito = subambitobd.get();
			 return subambito;
		 }
		 return null;
	}

	@Override
	public RestResponse guardar(Ambito subambito) {
		
		//SubAmbito subambit = ambitoDao.findByNombre(subambito.getNombre());
		Ambito subambit = ambitoDao.findByNombre(subambito.getNombre());
		
		if(subambit!=null) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), "EL  NOMBRE YA EXISTE");
		}
		
		
		if(subambito.getNombre()!=null) {
				ambitoDao.save(subambito);
					return new RestResponse(HttpStatus.OK.value(), "REGISTRO CORRECTO");
		}
		return null;
	}

	@Override
	public Iterable<Ambito> listarSubAmbitosActivosByAmbitoId(Long id) {
		Iterable<Ambito> subambitos = ambitoDao.listarSubAmbitoByAmbitoId(id);
		List<Ambito> subambitolst = StreamSupport.stream(subambitos.spliterator(), false).collect(Collectors.toList());
		subambitolst.removeIf(subambito -> !subambito.isActivo());
		return subambitolst;
	}

	@Override
	public RestResponse actualizar(Ambito subambito) {
		
		Ambito subambit = ambitoDao.findByNombre(subambito.getNombre());
		
		if(subambit!=null) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), "EL  NOMBRE YA EXISTE");
		}
		
		if(subambito.getNombre()!=null) {
			ambitoDao.save(subambito);
				return new RestResponse(HttpStatus.OK.value(), "REGISTRO CORRECTO");
		}
	return null;
	}

}


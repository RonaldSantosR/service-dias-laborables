package com.udemy.service.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.tomcat.util.http.fileupload.util.Streams;
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
		Ambito subambit = new Ambito();
		if(subambito.getNombre().equals("")) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), "FALTA INGRESAR DATOS");
		}
			subambit = ambitoDao.findByNombre(subambito.getNombre());

		//boolean constante=true;
		
		if(subambit==null) {
			ambitoDao.save(subambito);
			return new RestResponse(HttpStatus.OK.value(), "REGISTRO CORRECTO");
			
		}else {
			if(subambito.getId()==subambit.getId()) {
				ambitoDao.save(subambito);
				return new RestResponse(HttpStatus.OK.value(), "REGISTRO CORRECTO");				
			}else {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(), "EL NOMBRE YA EXISTE");

			}
		}

	}

	@Override
	public Iterable<Ambito> listarByIds(List<Long> ids) {
		return ambitoDao.findAllById(ids);
	}

	@Override
	public Iterable<Ambito> listarAmbitoByRegionId(Long regionId) {
		return ambitoDao.findAmbitosByRegionId(regionId);
	}

	@Override
	public List<Long> listarAmbitosIdByNombre(List<String> nombres) {
		Iterable<Ambito> ambitos = ambitoDao.findAllByNombreIn(nombres);
		List<Ambito> ambitoslst = StreamSupport.stream(ambitos.spliterator(), false).collect(Collectors.toList());
		List<Long> ambitosIds = new ArrayList<>();
		int i=0;
		while(i<ambitoslst.size()){
			ambitosIds.add(ambitoslst.get(i).getId());
			i++;
		}
		return ambitosIds;
	}

}


package com.udemy.service.classes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.udemy.entity.SubAmbito;
import com.udemy.repository.ISubAmbitoDao;
import com.udemy.service.interfaces.ISubAmbitoService;
import com.udemy.util.RestResponse;


@Service
public class SubAmbitoService implements ISubAmbitoService {
	
	@Autowired
	ISubAmbitoDao subambitoDao;
	
	@Override
	public Iterable<SubAmbito> listarAll() {
		return subambitoDao.findAll();
	}

	@Override
	public SubAmbito listarById(Long id) {
		 Optional<SubAmbito> subambitobd =	subambitoDao.findById(id);
		 if(subambitobd.isPresent()) {
			 SubAmbito subambito = subambitobd.get();
			 return subambito;
		 }
		 return null;
	}

	@Override
	public RestResponse guardar(SubAmbito subambito) {
		if(subambito.getNombre()!=null) {
			try {
				subambitoDao.save(subambito);
					return new RestResponse(HttpStatus.OK.value(), "REGISTRO CORRECTO");
			}catch( javax.persistence.PersistenceException  ex){
				if(ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
					return new RestResponse(HttpStatus.BAD_REQUEST.value(), "DOBLE INGRESO DE NOMBRE");
				}

			}
		}
		return null;
	}

	@Override
	public Iterable<SubAmbito> listarSubAmbitosActivosByAmbitoId(Long id) {
		Iterable<SubAmbito> subambitos = subambitoDao.listarSubAmbitoByAmbitoId(id);
		List<SubAmbito> subambitolst = StreamSupport.stream(subambitos.spliterator(), false).collect(Collectors.toList());
		subambitolst.removeIf(subambito -> !subambito.isActivo());
		return subambitolst;
	}

}


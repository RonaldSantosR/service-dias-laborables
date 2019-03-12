package com.udemy.service.classes;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.udemy.component.examplecomponent;
import com.udemy.entity.Ambito;
import com.udemy.entity.Dia;
import com.udemy.entity.DiaHora;
import com.udemy.entity.Feriado;
import com.udemy.repository.AmbitoRepository;
import com.udemy.repository.DiaRepository;
import com.udemy.repository.DiahoraRepository;
import com.udemy.service.interfaces.IAmbitoService;
import com.udemy.util.RestResponse;

import ch.qos.logback.classic.Logger;


@Service
public class AmbitoService implements IAmbitoService{
	@Autowired
	private AmbitoRepository ambitorepository;
	@Autowired
	private DiahoraRepository diahorarepository;
	List<DiaHora> diah;
	@Override
	public RestResponse Registrar(Ambito ambito) {
		if(!validarcampos(ambito)) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(),"Verifique los datos ingresados");

		}else {
			/*for(Feriado feriado : ambito.getFeriados()) {
			}*/
			ambitorepository.save(ambito);
			return new RestResponse(HttpStatus.OK.value(),"Operacion Exitosa");
		}
	}
	
	public RestResponse Actualizar(Ambito ambito,Long id) {
				/*
		
		int i = 0; 
		while(i < buzones.size()) {
			if (documento.getEnvio().getBuzonId() == Long.valueOf(buzones.get(i).get("id").toString())) {
				documento.getEnvio().setBuzon(buzones.get(i));
				break;
			}
			i++;
		}		
		*/
		
		/*
		
		for(DiaHora diahora: ambito.getDiasHora()) {
			diahora.setAmbito(ambito); 
			
		}
		*/
		
		Ambito ambitoid = ambitorepository.findById(id).orElse(null);
		if(ambitoid==null) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(),"No se ha encontrado el Id - Verifique los datos");
		}
		
		
		if(!validarcampos(ambito)) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(),"Verifique los datos ingresados");			
		}else {
			for(DiaHora diahora: ambito.getDiasHora()) {
				diahorarepository.save(diahora);
			}
			ambitorepository.save(ambito);
			return new RestResponse(HttpStatus.OK.value(),"Se ha modificado correctamente los datos del ámbito");

		}

	
	}
	
	
	public boolean validarcampos(Ambito ambito) {
		List<Long> ids = new ArrayList<Long>();
		boolean constante = true; 
		
		if(ambito.getNombre().equals("")) {
			constante=false;
		}
		for(DiaHora diahora : ambito.getDiasHora() ) {
			if(diahora.getInicio().compareTo(diahora.getFin())>=0) {
				constante=false;
			}
			Dia dia = diahora.getDia();
			if(dia.getId()<0) {
				constante  = false;
			}
			if(dia.getId()>7) {
				constante  = false;
			}
			ids.add(dia.getId());
		}
		HashMap codigos = new HashMap();
		
		for(Long num : ids) {
			codigos.put(num,0);
		}
		
		if(ids.size()!=codigos.size()) {
			constante = false;
		}
		
		
		return constante;
	}

	@Override
	public Ambito ListarById(Long id) {
		
		return ambitorepository.findById(id).orElse(null);
	}

}

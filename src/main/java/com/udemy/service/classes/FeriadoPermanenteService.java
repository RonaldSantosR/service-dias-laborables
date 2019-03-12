package com.udemy.service.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.entity.Ambito;
import com.udemy.entity.FeriadoPermanente;
import com.udemy.repository.FeriadoPRepository;
import com.udemy.service.interfaces.IFeriadoService;

@Service
public class FeriadoPermanenteService implements IFeriadoService {
	

	@Autowired
	private FeriadoPRepository feriadoprepository;
	
	@Override
	public FeriadoPermanente registrar(FeriadoPermanente feriadopermanente,Ambito ambito) {
		

		feriadopermanente.setAmbito(ambito);

		
		return  feriadoprepository.save(feriadopermanente);
	}

	
	
	
}

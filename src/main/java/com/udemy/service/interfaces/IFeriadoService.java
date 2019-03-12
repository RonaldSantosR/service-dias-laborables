package com.udemy.service.interfaces;

import com.udemy.entity.Ambito;
import com.udemy.entity.FeriadoPermanente;

public interface IFeriadoService {
	
	FeriadoPermanente registrar(FeriadoPermanente feriadopermanente,Ambito ambito);
	
	
}

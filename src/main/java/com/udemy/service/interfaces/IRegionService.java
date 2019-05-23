package com.udemy.service.interfaces;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.udemy.entity.Region;
import com.udemy.entity.DiaHora;
import com.udemy.model.modelRegion;
import com.udemy.util.RestResponse;

public interface IRegionService {
			
	RestResponse Registrar(String ambito) throws JsonParseException, JsonMappingException, IOException, ParseException;
	RestResponse Actualizar(String ambito,Long id) throws JsonParseException, JsonMappingException, IOException, ParseException;
	Region ListarById(Long id); 
	Iterable<Region> ListarAmbitos() throws JsonProcessingException;
	Iterable<DiaHora> listarhoras(Long id);
	List<modelRegion> ListarModalAmbitos();
	Date listarFechaLimite(String fechaInicial,Long ambitoId, double dias) throws ParseException;
	boolean validarDiaLaborable(Date fecha, Long ambitoId) throws ParseException;
}

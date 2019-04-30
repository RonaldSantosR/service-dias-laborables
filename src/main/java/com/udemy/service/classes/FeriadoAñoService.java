package com.udemy.service.classes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.udemy.repository.AmbitoRepository;
import com.udemy.repository.FeriadoARepository;
import com.udemy.repository.FeriadoRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.entity.Ambito;
import com.udemy.entity.Dia;
import com.udemy.entity.DiaHora;
import com.udemy.entity.FeriadoAño;
import com.udemy.entity.Feriado;
import com.udemy.entity.FeriadoPermanente;
import com.udemy.model.modelferiadoaño;
import com.udemy.model.modelferiadopermanente;
import com.udemy.service.interfaces.IFeriadoAñoService;
import com.udemy.util.RestResponse;

@Service
public class FeriadoAñoService implements IFeriadoAñoService {
	private final static String MENSAJE_VERIFICAR = "Verifique los datos ingresados";

	private static final Log Logger = LogFactory.getLog(FeriadoAñoService.class);
	protected ObjectMapper mapper;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	Date date = new Date();

	@Autowired
	private AmbitoRepository ambitorepository;

	@Autowired
	private FeriadoARepository feriadoarepository;

	@Autowired
	private FeriadoRepository feriadorepository;

	@Override
	public RestResponse registrar(String feriadoaño,Map<String, String> pathVariablesMap) throws IOException {
		
		if (pathVariablesMap.containsKey("id")) {
		Long id = Long.parseLong(pathVariablesMap.get("id"));	
		Date date = new Date();
		this.mapper = new ObjectMapper();
		mapper.isEnabled(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);

		modelferiadoaño modelferiadoaño;
		try {
			modelferiadoaño = this.mapper.readValue(feriadoaño, modelferiadoaño.class);
		} catch (Exception e) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
		}

		try {
			mapper.reader(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY).readTree(feriadoaño);
		} catch (JsonMappingException e) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), MENSAJE_VERIFICAR);
		}

		Ambito ambito = ambitorepository.findById(id).orElse(null);

		if (ambito == null) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), "No se ha encontrado el Id - Verifique los datos");
		}

		modelferiadoaño.setAmbito(ambito);

		//SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
		Date dateI = null;
		try {
			try {
				dateI = dateFormat.parse(modelferiadoaño.getFecha());
			} catch (Exception e) {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
			}

			if (!validarfecha(modelferiadoaño.getFecha())) {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
			}

			FeriadoAño feriado = new FeriadoAño();

			try {
				feriado = convertir(modelferiadoaño);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (!validarferiado(feriado)) {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
			} else {
				if (compararfechas(feriado.getFecha())) {
					if (repetirfecha(feriado.getFecha(),id)) {
						
						if(comprobarconpermanente(feriado.getFecha(),id)) {
							feriadoarepository.save(feriado);
							return new RestResponse(HttpStatus.OK.value(),"Se ha registrado correctamente los datos del ámbito");
						}else {
							return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);

						}
					} else {
						return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Repites el feriado");
					}

				} else {
					return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
				}
				// DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				// Logger.info("Año Completo " + date);
			}
		} catch (

		NullPointerException e) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
		}
		}else {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), "No se ha encontrado el feriado a eliminar");
		}
	}

	public boolean comprobarconpermanente(Date fecha,Long id) {
		
		boolean constante = true;
		DateFormat dateFormat = new SimpleDateFormat("dd-MM");
		DateFormat dateFormataño = new SimpleDateFormat("yyyy");
		String dia = dateFormat.format(fecha);
		Iterable<Feriado> feriados = feriadorepository.findAll();
		List<String> fechas = new ArrayList<String>();
		
		if(feriados==null) {
			return true;
		}
		
		
		for (Feriado feriado : feriados) {
			Ambito ambito = feriado.getAmbito();
			String Stringaño = dateFormataño.format(feriado.getFecha());
			String Stringdiames = dateFormat.format(feriado.getFecha());
			
			if(ambito.getId()==id) {
				if(Stringaño.equals("1970") || Stringaño.equals("1980")) {
					fechas.add(Stringdiames);
				}
			}

		}
		fechas.add(dia);
		HashMap codigosfechas = new HashMap();
		for (String dates : fechas) {
			codigosfechas.put(dates, 0);
		}

		if (codigosfechas.size() != fechas.size()) {
			return false;
		}
		return true;
	}

	public FeriadoAño convertir(modelferiadoaño modelfe) throws ParseException {

		FeriadoAño per = new FeriadoAño();

		per.setAmbito(modelfe.getAmbito());
		per.setNombre(modelfe.getNombre());
		per.setAsunto(modelfe.getAsunto());

		//SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = dateFormat.parse(modelfe.getFecha());
		per.setFecha(date);

		return per;

	}

	public boolean repetirfecha(Date fecha,Long id) {

		Iterable<Feriado> feriados = feriadorepository.findAll();
		List<Date> fechas = new ArrayList<Date>();
		
		if(feriados==null) {
			return true;
		}
		
		for (Feriado feriado : feriados) {
			Ambito ambito = feriado.getAmbito();
			if(ambito.getId()==id) {
				fechas.add(feriado.getFecha());
			}
		}

		fechas.add(fecha);

		HashMap codigosfechas = new HashMap();
		for (Date dates : fechas) {
			codigosfechas.put(dates, 0);
		}

		if (codigosfechas.size() != fechas.size()) {
			return false;
		}
		return true;

	}

	public boolean compararfechas(Date fecha) {
		//DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		//SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String dia = dateFormat.format(date);
		Date dates = null;
		try {
			dates = dateFormat.parse(dia);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (fecha.compareTo(dates) > 0) {
			return true;
		}
		return false;
	}

	public boolean validarfecha(String fecha) {
		try {
			//SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
			dateFormat.setLenient(false);
			dateFormat.parse(fecha);
		} catch (ParseException e) {
			return false;
		}
		
		
		String max = fecha.substring(10);
		Logger.info("TE MUESTRA ESTOs : " + max);			
		if(!max.equals("")) {
			return false;
		}
		return true;
	}



	public boolean validarferiado(FeriadoAño feriadoAño) {

		boolean constante = true;

		if (feriadoAño.getFecha().equals("")) {
			constante = false;
		}

		if (feriadoAño.getNombre().equals("")) {
			constante = false;
		}

		if (feriadoAño.getAsunto().equals("")) {
			constante = false;
		}

		return constante;
	}

	@Override
	public RestResponse eliminar(Map<String, String> pathVariablesMap)  {
		Feriado feriadoaño;
		//DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String fecha = dateFormat.format(date);
		Date date=null;
		try {
			date = dateFormat.parse(fecha);
		} catch (ParseException e) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(),MENSAJE_VERIFICAR);
		}
		try{
		if (pathVariablesMap.containsKey("id")) {
			Long id = Long.parseLong(pathVariablesMap.get("id"));
			feriadoaño = feriadoarepository.findById(id).orElse(null);

			if (feriadoaño == null) {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(), "No se ha encontrado el feriado a eliminar");
			}

			if(feriadoaño.getFecha().compareTo(date)<0) {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(),MENSAJE_VERIFICAR);
			}
			
			
			feriadoarepository.deleteById(id);
			return new RestResponse(HttpStatus.OK.value(), "Se ha eliminado el feriado permanente del sistema");

		} else {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), "No se ha encontrado el feriado a eliminar");
		}
		}catch(NumberFormatException ex){ // handle your exception
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), "No se ha encontrado el feriado a eliminar");
		}
		
	}	

}

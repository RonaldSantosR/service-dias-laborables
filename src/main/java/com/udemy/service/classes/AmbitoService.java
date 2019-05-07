package com.udemy.service.classes;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import com.udemy.model.*;
import java.io.Console;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.udemy.component.examplecomponent;
import com.udemy.entity.Ambito;
import com.udemy.entity.Dia;
import com.udemy.entity.DiaHora;
import com.udemy.entity.Feriado;
import com.udemy.repository.AmbitoRepository;
import com.udemy.repository.DiaRepository;
import com.udemy.repository.DiahoraRepository;
import com.udemy.repository.FeriadoRepository;
import com.udemy.service.interfaces.IAmbitoService;
import com.udemy.util.RestResponse;

@Service
public class AmbitoService implements IAmbitoService {

	private final static String MENSAJE_VERIFICAR = "Verifique los datos ingresados";

	@Autowired
	private AmbitoRepository ambitorepository;
	@Autowired
	private DiahoraRepository diahorarepository;
	@Autowired
	private FeriadoRepository feriados;
	List<DiaHora> diah;
	protected ObjectMapper mapper;
	private static final Log Logger = LogFactory.getLog(AmbitoService.class);
	

	
	@Override
	public RestResponse Registrar(String ambitoss)
			throws JsonParseException, JsonMappingException, IOException, ParseException {

		this.mapper = new ObjectMapper();
		mapper.isEnabled(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
		boolean respuesta = true;
		modelAmbito modelambito;
		try {
			modelambito = this.mapper.readValue(ambitoss, modelAmbito.class);
	
			for (modelDiaHora diahora : modelambito.getDiaLaborable()) {
				LocalTime Inicio = null;
				LocalTime Fin = null;
				try {
					Inicio = LocalTime.parse(diahora.getInicio());
					Fin = LocalTime.parse(diahora.getFin());
				} catch (DateTimeParseException e) {
					return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
				}

				if (diahora.getInicio().compareTo(diahora.getFin()) >= 0) {
					return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
				}

			}

		} catch (NullPointerException e) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
		}

		respuesta = validarmetodo(modelambito, ambitoss);
		Set<DiaHora> sets = new HashSet<DiaHora>();
		if (respuesta == false) {

			return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);

		} else {
			Ambito ambitos = new Ambito();

			ambitos.setId(modelambito.getId());

			DiaHora diahora = new DiaHora();

			ambitos.setNombre(modelambito.getNombre());
			for (modelDiaHora model : modelambito.getDiaLaborable()) {
				diahora = converterdiahora(model);
				sets.add(diahora);

			}
			ambitos.setDiaLaborable(sets);
			//setDiasHora(sets);

			ambitorepository.save(ambitos);
			return new RestResponse(HttpStatus.OK.value(), "Operacion Exitosa");
		}
	}

	public DiaHora converterdiahora(modelDiaHora modeldia) throws ParseException {
		DiaHora dia = new DiaHora();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		dia.setId(modeldia.getId());
		dia.setInicio(sdf.parse(modeldia.getInicio()));
		dia.setFin(sdf.parse(modeldia.getFin()));
		dia.setActivo(modeldia.getActivo());
		dia.setDia(converterdia(modeldia.getDia()));
		return dia;
	}

	public Dia converterdia(modelDia model) {
		Dia dias = new Dia();
		dias.setId(model.getId());
		dias.setNombre(model.getNombre());
		return dias;
	}

	public boolean validarmetodo(modelAmbito Ambitoss, String usuarios) throws IOException {
		boolean respuesta = true;
		try {
			mapper.reader(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY).readTree(usuarios);
			respuesta = validarcampos(Ambitoss);
		} catch (JsonMappingException e) {
			respuesta = false;
		} catch (NullPointerException e) {
			respuesta = false;
		}
		return respuesta;
	}

	public RestResponse Actualizar(String ambito, Long id)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		Logger.info("Llega hasta aqui");
		Iterable<DiaHora> diashoras = diahorarepository.findhorasById(id);
		List<DiaHora> listadiashora = new ArrayList<>();
		for(DiaHora diahorahora :diashoras) {
			listadiashora.add(diahorahora);
		}
		boolean probar = true;
		modelAmbito modelambito;
		boolean respuesta = true;
		this.mapper = new ObjectMapper();
		mapper.isEnabled(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);

		try {
			modelambito = this.mapper.readValue(ambito, modelAmbito.class);

			Logger.info("ModelAmbito nombre :" + modelambito.getNombre());
			for (modelDiaHora diahora : modelambito.getDiaLaborable()) {
				Logger.info("ModelAmbito Inicio :" + diahora.getInicio());
				Logger.info("ModelAmbito Fin :" + diahora.getFin());
				LocalTime Inicio = null;
				LocalTime Fin = null;
				try {
					Inicio = LocalTime.parse(diahora.getInicio());
					Fin = LocalTime.parse(diahora.getFin());
				} catch (DateTimeParseException e) {
					return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
				}

				if (diahora.getInicio().compareTo(diahora.getFin()) >= 0) {
					return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
				}

			}
		} catch (NullPointerException e) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
		}

		Ambito ambitoid = ambitorepository.findById(id).orElse(null);
		if (ambitoid == null) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), "No se ha encontrado el Id - Verifique los datos");
		}

		respuesta = validarmetodo(modelambito, ambito);

		modelambito.setId(id);
		if (!respuesta) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
		} else {
			Set<DiaHora> sets = new HashSet<DiaHora>();
			DiaHora diahora = new DiaHora();
			Ambito ambitos = new Ambito();
			
			ambitoid.setNombre(modelambito.getNombre());
			
			for(DiaHora diadiahora : listadiashora) {

			for (modelDiaHora model : modelambito.getDiaLaborable()) {
				diahora = converterdiahora(model);
				diahora.setAmbito(ambitoid);
				diahorarepository.save(diahora);				
			}
			
			}
			
			ambitorepository.save(ambitoid);
			return new RestResponse(HttpStatus.OK.value(), "Se ha modificado correctamente los datos del ámbito");
		}
	}

	public boolean validarcampos(modelAmbito ambito) {
		List<Long> ids = new ArrayList<Long>();
		boolean constante = true;
		Long idds = 1L;
		int item = 0;

		if (ambito.getNombre().equals("")) {
			constante = false;
		}
		for (modelDiaHora diahora : ambito.getDiaLaborable()) {

			modelDia dia = diahora.getDia();
			if (dia.getId() < 0) {
				constante = false;
			}
			if (dia.getId() > 7) {
				constante = false;
			}
			ids.add(dia.getId());
			item++;
		}
		HashMap codigos = new HashMap();

		for (Long num : ids) {
			codigos.put(num, 0);
		}

		if (ids.size() != codigos.size()) {
			constante = false;
		}

		if (item == 0) {
			constante = false;
		}
		return constante;
	}

	@Override
	public Ambito ListarById(Long id) {
		return ambitorepository.findById(id).orElse(null);
	}

	@Override
	public Iterable<Ambito> ListarAmbitos() throws JsonProcessingException {
		Iterable<Ambito> ambitos = ambitorepository.findAll();
		return ambitos;

	}

	@Override
	public Iterable<DiaHora> listarhoras(Long id) {
		return diahorarepository.findhorasById(id);
	}

	@Override
	public List<modelAmbito> ListarModalAmbitos() {
		Iterable<Ambito> ambitos = ambitorepository.findAll();
		List<modelAmbito> modelAmbitos =  new ArrayList<>();
		for(Ambito ambito : ambitos) {
			modelAmbito modelambito = new modelAmbito();
			Set<modelDiaHora> sets = new HashSet<modelDiaHora>();		
			modelambito.setId(ambito.getId());
			modelambito.setNombre(ambito.getNombre());
			Logger.info("ddasads " + modelambito.getId());			
			Logger.info("ddasads " + modelambito.getNombre());
			for (DiaHora diahora : ambito.getDiaLaborable()) {
				modelDiaHora modeldia = new modelDiaHora();								
				modeldia = convertirdiahoras(diahora);
				Logger.info("ddasads " + modeldia.getId());
				sets.add(modeldia);
			}
			modelambito.setDiaLaborable(sets);		
			modelAmbitos.add(modelambito);
		}

		return modelAmbitos;
	}
	

	
	public modelDiaHora convertirdiahoras(DiaHora diaLaborable) {
		Set<modelDiaHora> modeldiashoras = new HashSet<>();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		modelDiaHora modeldiahora  =new modelDiaHora();
		modeldiahora.setId(diaLaborable.getId());
		modeldiahora.setInicio(sdf.format(diaLaborable.getInicio()));
		modeldiahora.setFin(sdf.format(diaLaborable.getFin()));
		modeldiahora.setDia(convertirdia(diaLaborable.getDia()));
		modeldiahora.setActivo(diaLaborable.getActivo());
		return modeldiahora;
	}

	public modelAmbito convertirambito(Ambito ambito) {
		modelAmbito modelambito= new modelAmbito();
		modelambito.setId(ambito.getId());
		modelambito.setNombre(ambito.getNombre());
		return modelambito;
	}
	
	
	public modelDia convertirdia(Dia dia) {
		modelDia modeldia = new modelDia();
		modeldia.setId(dia.getId());
		modeldia.setNombre(dia.getNombre());
		return modeldia;
	}
	


}

package com.udemy.service.classes;



import com.udemy.model.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.entity.Region;
import com.udemy.entity.Dia;
import com.udemy.entity.DiaHora;
import com.udemy.repository.RegionRepository;
import com.udemy.repository.DiaRepository;
import com.udemy.repository.DiahoraRepository;
import com.udemy.repository.FeriadoRepository;
import com.udemy.service.interfaces.IRegionService;
import com.udemy.util.RestResponse;

@Service
public class RegionService implements IRegionService {

	private final static String MENSAJE_VERIFICAR = "Verifique los datos ingresados";

	@Autowired
	private RegionRepository regionrepository;
	@Autowired
	private DiahoraRepository diahorarepository;
	@Autowired
	private FeriadoRepository feriados;
	@Autowired
	private DiaRepository diarepository;
	
	List<DiaHora> diah;
	protected ObjectMapper mapper;
	private static final Log Logger = LogFactory.getLog(RegionService.class);
	


	
	@Override
	public RestResponse Registrar(String ambitoss)
			throws JsonParseException, JsonMappingException, IOException, ParseException {

		this.mapper = new ObjectMapper();
		mapper.isEnabled(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
		boolean respuesta = true;
		modelRegion modelambito;
		try {
			modelambito = this.mapper.readValue(ambitoss, modelRegion.class);
	
			for (modelDiaHora diahora : modelambito.getDiasLaborables()) {
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
			Region ambitos = new Region();

			ambitos.setId(modelambito.getId());

			DiaHora diahora = new DiaHora();

			ambitos.setNombre(modelambito.getNombre());
			for (modelDiaHora model : modelambito.getDiasLaborables()) {
				diahora = converterdiahora(model);
				sets.add(diahora);

			}
			ambitos.setDiasLaborables(sets);
			//setDiasHora(sets);

			regionrepository.save(ambitos);
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

	public boolean validarmetodo(modelRegion Ambitoss, String usuarios) throws IOException {
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
		modelRegion modelambito;
		boolean respuesta = true;
		this.mapper = new ObjectMapper();
		mapper.isEnabled(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);

		try {
			modelambito = this.mapper.readValue(ambito, modelRegion.class);

			Logger.info("ModelAmbito nombre :" + modelambito.getNombre());
			for (modelDiaHora diahora : modelambito.getDiasLaborables()) {
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
					return new RestResponse(HttpStatus.BAD_REQUEST.value(), "La hora inicial no puede ser mayor a la hora final");
				}

			}
		} catch (NullPointerException e) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
		}

		Region ambitoid = regionrepository.findById(id).orElse(null);
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
			Region ambitos = new Region();
			
			ambitoid.setNombre(modelambito.getNombre());
			
			for(DiaHora diadiahora : listadiashora) {

			for (modelDiaHora model : modelambito.getDiasLaborables()) {
				diahora = converterdiahora(model);
				diahora.setRegion(ambitoid);
				diahorarepository.save(diahora);				
			}
			
			}
			
			regionrepository.save(ambitoid);
			return new RestResponse(HttpStatus.OK.value(), "Se ha modificado correctamente los datos del ámbito");
		}
	}

	public boolean validarcampos(modelRegion ambito) {
		List<Long> ids = new ArrayList<Long>();
		boolean constante = true;
		Long idds = 1L;
		int item = 0;

		if (ambito.getNombre().equals("")) {
			constante = false;
		}
		for (modelDiaHora diahora : ambito.getDiasLaborables()) {

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
	public Region ListarById(Long id) {
		return regionrepository.findById(id).orElse(null);
	}

	@Override
	public Iterable<Region> ListarAmbitos() throws JsonProcessingException {
		Iterable<Region> ambitos = regionrepository.findAll();
		return ambitos;

	}

	@Override
	public Iterable<DiaHora> listarhoras(Long id) {
		return diahorarepository.findhorasById(id);
	}

	@Override
	public List<modelRegion> ListarModalAmbitos() {
		Iterable<Region> ambitos = regionrepository.findAll();
		List<modelRegion> modelAmbitos =  new ArrayList<>();
		for(Region ambito : ambitos) {
			modelRegion modelambito = new modelRegion();
			Set<modelDiaHora> sets = new HashSet<modelDiaHora>();		
			modelambito.setId(ambito.getId());
			modelambito.setNombre(ambito.getNombre());
			Logger.info("ddasads " + modelambito.getId());			
			Logger.info("ddasads " + modelambito.getNombre());
			for (DiaHora diahora : ambito.getDiasLaborables()) {
				modelDiaHora modeldia = new modelDiaHora();								
				modeldia = convertirdiahoras(diahora);
				Logger.info("ddasads " + modeldia.getId());
				sets.add(modeldia);
			}
			modelambito.setDiasLaborables(sets);		
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

	public modelRegion convertirambito(Region ambito) {
		modelRegion modelambito= new modelRegion();
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

	@Override
	public Date listarFechaLimite(String fechaInicial, Long ambitoId, double horas, int tipo) throws ParseException {
		//		SimpleDateFormat  dt = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
 		//dt.setTimeZone(TimeZone.getTimeZone("America/Peru"));
		String primercorte = "09:00:00";
		String finprimercorte = "13:00:00";
		String finsegundocorte="18:00:00";	
		SimpleDateFormat dthora = new SimpleDateFormat("HH:mm:ss");	
		SimpleDateFormat dtdia = new SimpleDateFormat("yyyy-MM-dd");
		Date fechapartida= new Date();		
		Date dateI= new Date();
		Date dateII= new Date();
		Date datehora= new Date();
		Date otrodate= new Date();
		Date pruebadate= new Date();

		try {
			dateI = dt.parse(fechaInicial);
		} catch (Exception e) {
			return null;
		}
		
		try {
			dateII = dt.parse(fechaInicial);
		} catch (Exception e) {
			return null;
		}		
		
		datehora =  dthora.parse(dthora.format(datehora));

		
		if(tipo==3) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateI);
			calendar.add(Calendar.HOUR_OF_DAY, (int) horas);	
			Date utilDate = calendar.getTime();
			return utilDate;
		}	
		
		Logger.info( "asdassadad");

		///////////////////////////////////////////////
		
		Calendar calendarx = Calendar.getInstance();
		pruebadate=dt.parse( dtdia.format(dateI)+" "+primercorte);
		Logger.info( dt.format(pruebadate));
		calendarx.setTime(pruebadate);
		calendarx.add(Calendar.HOUR_OF_DAY,1);		
		String noprimercorte = dthora.format(calendarx.getTime());
		
		/*if((dthora.parse(primercorte).compareTo(dthora.parse(dthora.format(dateI))) <= 0) && (dthora.parse(noprimercorte).compareTo(dthora.parse(dthora.format(dateI))) >= 0)) {
			fechapartida = dthora.parse(primercorte);
			dateI=dt.parse( dtdia.format(dateI)+" "+dthora.format(fechapartida));
		}*/
		
		
		if((dthora.parse(noprimercorte).compareTo(dthora.parse(dthora.format(dateI))) < 0) && (dthora.parse(finprimercorte).compareTo(dthora.parse(dthora.format(dateI))) > 0)) {
			fechapartida = dthora.parse(finprimercorte);
			dateI=dt.parse( dtdia.format(dateI)+" "+dthora.format(fechapartida));
		}
		
		Calendar calendary = Calendar.getInstance();
		pruebadate=dt.parse( dtdia.format(dateI)+" "+finprimercorte);
		calendary.setTime(pruebadate);
		calendary.add(Calendar.HOUR_OF_DAY,1);		
		String nofinprimercorte = dthora.format(calendary.getTime());
		
		/*if((dthora.parse(finprimercorte).compareTo(dthora.parse(dthora.format(dateI))) <= 0) && (dthora.parse(nofinprimercorte).compareTo(dthora.parse(dthora.format(dateI))) >= 0)) {
			fechapartida = dthora.parse(finprimercorte);
			dateI=dt.parse( dtdia.format(dateI)+" "+dthora.format(fechapartida));
		}*/		
		
		if( dthora.parse(nofinprimercorte).compareTo(dthora.parse(dthora.format(dateI))) < 0) {
			fechapartida = dthora.parse(primercorte);
			dateI=dt.parse( dtdia.format(dateI)+" "+dthora.format(fechapartida));	
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(dateI);
			boolean condicion=true;
			int i=0;
			i++;
			while(condicion) {
				calendar2.add(calendar2.DATE, i);
				if(validarDiaLaborable(calendar2.getTime(),ambitoId)){
					condicion=false;
				}						
				i++;											
			}
			dateI= calendar2.getTime();
			otrodate=calendar2.getTime();
		
		}		
		
		////////////////////////////////////////////////
		
		int i=0; //dias laborables
		int j=0; //dias calendarios
		//String valor = String.valueOf(horas);
  		
		if(horas<24) {
			
			String diaSemana = null;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateI);
			//Date utilDates= calendar.getTime();
			diaSemana = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
			
			calendar.add(Calendar.HOUR_OF_DAY, (int) horas);			
			
			int dia = 0;

			if(Integer.parseInt(diaSemana)==1) {
				dia=7;
			}else {
				dia= Integer.parseInt(diaSemana) - 1;
			}
			
			Date utilDate = calendar.getTime();
			//Date horadate =  diahorarepository.horasalida(Long.valueOf(dia) ,  ambitoId);
			
			if((dthora.parse(primercorte).compareTo(dthora.parse(dthora.format(dateI))) <= 0) && (dthora.parse(noprimercorte).compareTo(dthora.parse(dthora.format(dateI))) >= 0)) {

				return utilDate;
				
			}
			if((dthora.parse(primercorte).compareTo(dthora.parse(dthora.format(utilDate))) < 0) && (dthora.parse(finprimercorte).compareTo(dthora.parse(dthora.format(utilDate))) >= 0)) {
				//Calendar calendar = Calendar.getInstance();
				//calendar.add(Calendar.HOUR_OF_DAY, (int) horas);			
				

				utilDate = dt.parse(dtdia.format(utilDate)+" "+finprimercorte);
				return utilDate;
			}
			
			
			if( (dthora.parse(finprimercorte).compareTo( dthora.parse(dthora.format(utilDate))) < 0 && (dthora.parse(finsegundocorte).compareTo(dthora.parse(dthora.format(utilDate))) >= 0))){
				
				
				if((dthora.parse(finprimercorte).compareTo(dthora.parse(dthora.format(dateI))) <= 0) && (dthora.parse(nofinprimercorte).compareTo(dthora.parse(dthora.format(dateI))) >= 0)) {
					return utilDate;
				}
				
				Calendar calendar2 = Calendar.getInstance();
				calendar2.setTime(dateI);
				boolean condicion=true;
				i++;
				while(condicion) {
					calendar2.add(calendar2.DATE, i);
					if(validarDiaLaborable(calendar2.getTime(),ambitoId)){
						condicion=false;
					}						
					i++;											
				}
				utilDate = dt.parse(dtdia.format(utilDate)+" "+finsegundocorte);					
				return utilDate;

			}		
			
			
			if( dthora.parse(finsegundocorte).compareTo(dthora.parse(dthora.format(utilDate))) < 0 ) {
				
				Calendar calendar2 = Calendar.getInstance();
				calendar2.setTime(dateI);
				boolean condicion=true;
				i++;
				while(condicion) {
					calendar2.add(calendar2.DATE, i);
					if(validarDiaLaborable(calendar2.getTime(),ambitoId)){
						condicion=false;
					}						
					i++;											
				}
				
				utilDate = dt.parse(dtdia.format(calendar2.getTime())+" "+finprimercorte);					
				return utilDate;
				/*
				diaSemana = String.valueOf(calendar2.get(Calendar.DAY_OF_WEEK));
				
				if(Integer.parseInt(diaSemana)==1) {
					dia=7;
				}else {
					dia= Integer.parseInt(diaSemana) - 1;
				}				
				Date horaentrada=  diahorarepository.horaentrada(Long.valueOf(dia),ambitoId);
				
				Date fechalimite = new Date();
				Date fechalimiteparcial = calendar2.getTime();
				if(dthora.parse(dthora.format(horaentrada)).compareTo(dthora.parse(finprimercorte))<=0 ) {
					String fechastring = dtdia.format(fechalimiteparcial)+" "+finprimercorte;
					fechalimite = dt.parse(fechastring);
				}else {
					String fechastring = dtdia.format(fechalimiteparcial)+" "+finsegundocorte;
					fechalimite = dt.parse(fechastring);
				}	
				
				*/
			}				
			

			/*
			if (dthora.parse(finsegundocorte).compareTo(dthora.parse(dthora.format(utilDate))) > 0  ) {				
				
				return dthora.parse(finsegundocorte);
			}else {	
				
				
				
				Calendar calendar2 = Calendar.getInstance();
				calendar2.setTime(dateI);
				boolean condicion=true;
				i++;
				while(condicion) {
					calendar2.add(calendar2.DAY_OF_YEAR, i);
					if(validarDiaLaborable(calendar2.getTime(),ambitoId)){
						condicion=false;
					}						
					i++;											
				}
				diaSemana = String.valueOf(calendar2.get(Calendar.DAY_OF_WEEK));
				if(Integer.parseInt(diaSemana)==1) {
					dia=7;
				}else {
					dia= Integer.parseInt(diaSemana) - 1;
				}				
				Date horaentrada=  diahorarepository.horaentrada(Long.valueOf(dia),ambitoId);
				Date fechalimite = new Date();
				Date fechalimiteparcial = calendar2.getTime();
				if(dthora.parse(dthora.format(horaentrada)).compareTo(dthora.parse(finprimercorte))<=0 ) {
					String fechastring = dtdia.format(fechalimiteparcial)+" "+finprimercorte;
					fechalimite = dt.parse(fechastring);
				}else {
					String fechastring = dtdia.format(fechalimiteparcial)+" "+finsegundocorte;
					fechalimite = dt.parse(fechastring);
				}	
				return fechalimite;			
			}*/
		}
		
		Calendar calendarCalc = Calendar.getInstance();
		
		double diasCont= horas/24;
		
		while(i<diasCont) {
			j++;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateII);
			calendar.add(Calendar.DATE, j);
			Date fec = calendar.getTime();
			if(validarDiaLaborable(fec,ambitoId)){
				i++;
			}
			calendarCalc.setTime(calendar.getTime());
		}
		Date finaldia = new Date();
		finaldia=calendarCalc.getTime();
		if((dthora.parse(primercorte).compareTo( dthora.parse(dthora.format(dateII))) <= 0) && (dthora.parse(finprimercorte).compareTo(dthora.parse( dthora.format(dateII)) ) > 0)) {
			//return dthora.parse(finprimercorte);
			finaldia=dt.parse(dtdia.format(finaldia)+" "+finprimercorte);
		}
		
		if( (dthora.parse(finprimercorte).compareTo( dthora.parse(dthora.format(dateII))) <= 0) && (dthora.parse(finsegundocorte).compareTo(dthora.parse( dthora.format(dateII)) ) > 0)  ) {
			finaldia=dt.parse(dtdia.format(finaldia)+" "+finsegundocorte);
		}
		return finaldia;
	}

	@Override
	public boolean validarDiaLaborable (Date fecha, Long ambitoId) throws ParseException {
		SimpleDateFormat formatoDeldiames = new SimpleDateFormat("dd-MM");	
		SimpleDateFormat formatoDelaño = new SimpleDateFormat("YYYY-MM-dd");	

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		String diaSemana = null;
		boolean rpta=false;
		boolean rpta2=false;
		
		int dia = 0;
		diaSemana = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
		if(Integer.parseInt(diaSemana)==1) {
			dia=7;
		}else {
			dia= Integer.parseInt(diaSemana) - 1;
		}
		//formatoDelaño.parse(formatoDelaño.format(fecha)) ;
		//Feriado existeFeriado = feriados.esferiado(fecha,ambitoId);
		Iterable<Date> fechasaño = feriados.feriadoañofindporAmbito(ambitoId);
		for(Date feria : fechasaño){
			if(formatoDelaño.format(fecha).equals(formatoDelaño.format(feria)) ) {
				rpta2=true;
			}
		}
		if(rpta2) {
			return rpta;
		}	
		Iterable<Date> fechas= feriados.findporAmbito(ambitoId);
		for(Date feria : fechas){
			if(formatoDeldiames.format(fecha).equals(formatoDeldiames.format(feria)) ) {
				rpta2=true;
			}
		}
		if(rpta2) {
			return rpta;
		}		
				
		
		if(diahorarepository.esdialaborable(Long.valueOf(dia), ambitoId)){
			rpta=true;
		}
		return rpta;
	}

	@Override
	public Region findRegionByAmbito(Long ambitoId) throws ParseException {
		return regionrepository.findRegionByAmbitoId(ambitoId);
	}
	
	
}

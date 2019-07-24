package com.udemy.service.classes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.entity.Region;
import com.udemy.entity.Feriado;
import com.udemy.entity.TipoPeriodo;
import com.udemy.enumerator.EstadoTipoPeriodo;
import com.udemy.model.modelFeriado;
import com.udemy.model.modeltipoPeriodo;
import com.udemy.repository.RegionRepository;
import com.udemy.repository.FeriadoRepository;
import com.udemy.service.interfaces.IFeriadoService;
import com.udemy.util.RestResponse;

@Service
public class FeriadoPermanenteService implements IFeriadoService {
	private final static String MENSAJE_VERIFICAR = "Verifique los datos ingresados";
	private static final Log Logger = LogFactory.getLog(FeriadoPermanenteService.class);
	protected ObjectMapper mapper;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	Date date = new Date();

	@Autowired
	private FeriadoRepository feriadorepository;
	@Autowired
	private RegionRepository regionrepository;

	@Override
	public RestResponse registrar(String feriadopermanentes, Map<String, String> pathVariablesMap) throws IOException {

		if (pathVariablesMap.containsKey("id")) {
			Long id = Long.parseLong(pathVariablesMap.get("id"));
			this.mapper = new ObjectMapper();
			mapper.isEnabled(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
			modelFeriado feriadopermanentess;
			try {
				feriadopermanentess = this.mapper.readValue(feriadopermanentes, modelFeriado.class);
			} catch (Exception e) {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Verifique los datos ingresados");
			}

			try {
				mapper.reader(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY).readTree(feriadopermanentes);
			} catch (JsonMappingException e) {
				return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Mal Formato de Json");
			}

			Region ambito = regionrepository.findById(id).orElse(null);
			if (ambito == null) {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(),
						"No se ha encontrado el Id - Verifique los datos");
			}

			// necesito el Ambito ambito, modeloferiadopermanente feriadopermanente
			//feriadopermanentess.setAmbito(ambito);
			/*
			 * SimpleDateFormat sdf = new SimpleDateFormat("dd-MM"); String fechaComoCadena
			 * = sdf.format(feriadopermanente.getFecha()); Logger.info("Fecha a string " +
			 * fechaComoCadena);
			 */

			SimpleDateFormat dt = new SimpleDateFormat("dd-MM");
			Date dateI = null;
			Date dateF = null;
			try {
				try {
					dateI = dt.parse(feriadopermanentess.getFecha());
					dateF = dt.parse(feriadopermanentess.getFecha());
				} catch (Exception e) {
					return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Verifique los datos ingresados");
				}

				if (!malformato(feriadopermanentess.getFecha())) {
					return new RestResponse(HttpStatus.BAD_REQUEST.value(),
							"Verifique los datos ingresadossssssssssssssss");
				}

				feriadopermanentess.setFecha(completar(feriadopermanentess.getFecha()));
				if (!validarFecha(feriadopermanentess.getFecha())) {
					return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Verifique los datos ingresadoss");
				}
				Feriado feriado = new Feriado();

				try {
					feriado = convertirpermanentete(feriadopermanentess);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (!validarferiado(feriado)) {
					return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Verifique los datos ingresados");
				} else {
						return null;
//					if (repetirfecha(feriado.getFecha(), id)) {
//						feriadorepository.save(feriado);
//						return new RestResponse(HttpStatus.OK.value(),
//								"Se ha registrado correctamente los datos del ámbito");
//					} else {
//						return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Repites el feriado");
//					}
				}
			} catch (NullPointerException e) {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Verifique los datos ingresadosssss");
			}
		} else {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), "INGRESE LA ID DEL AMBITO");
		}
	}

	public String completar(String prefecha) {
		Date myDate = new Date();
		String fechapordefecto = "";
		GregorianCalendar calendar = new GregorianCalendar();
		String fecha = "-1970";
		String fechapar = "-1980";

		SimpleDateFormat sf = new SimpleDateFormat("yyyy");
		String fechaevaluar = sf.format(myDate);
		int numEntero = Integer.parseInt(fechaevaluar);

		if (calendar.isLeapYear(numEntero)) {
			fechapordefecto = prefecha + fechapar;
			// Logger.info("TE MUESTRA ESTO : " +numEntero);

		} else {
			fechapordefecto = prefecha + fecha;
			// Logger.info("TE MUESTRA ESTO : " + numEntero);
		}

		return fechapordefecto;
	}

	public Feriado convertir(modelFeriado modelferiados) throws ParseException {

		Feriado per = new Feriado();
		Logger.info("ENTRO A SERVICE6");

		per.setRegiones(modelferiados.getRegiones() );
		per.setNombre(modelferiados.getNombre());
		//per.setPeriodo(modelferiados.getPeriodo());
		Logger.info("ENTRO A SERVICE6");

		per.setTipoPeriodo(convertirtipo(modelferiados.getTipoperiodo()));
		Logger.info("ENTRO A SERVICE69");
		per.setId(modelferiados.getId());
		Logger.info("ENTRO A SERVICE67");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM");
		
		Date date = formatter.parse(modelferiados.getFecha());
		per.setFecha(date);
		Logger.info("ENTRO A SERVICE7");

		return per;

	}
	
	public modeltipoPeriodo convertirmodeltipo(TipoPeriodo tipoperido) {
		modeltipoPeriodo model = new modeltipoPeriodo();
		model.setId(tipoperido.getId());
		model.setNombre(tipoperido.getNombre());
		return model;
	}
	
	public TipoPeriodo convertirtipo(modeltipoPeriodo modeltipo) {
		TipoPeriodo tipo = new TipoPeriodo();
		tipo.setId(modeltipo.getId());
		tipo.setNombre(modeltipo.getNombre());
		return tipo;
	}
	
	
	

	public Feriado convertirpermanentete(modelFeriado modelferiados) throws ParseException {

		Feriado per = new Feriado();

		per.setRegiones(modelferiados.getRegiones() );
		per.setNombre(modelferiados.getNombre());
		per.setTipoPeriodo(convertirtipo(modelferiados.getTipoperiodo() ));
		per.setId(modelferiados.getId());
		Date date = dateFormat.parse(modelferiados.getFecha());
		per.setFecha(date);

		return per;

	}	
	
	
	public boolean repetirfecha(Feriado feriado) {
		Iterable<Date> fechas = null;
		Logger.info("ASDADDASDA");
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM");
		
		for(Region ambito : feriado.getRegiones()) {
						
			fechas = feriadorepository.findporAmbito(ambito.getId());
		
		}
		
		for(Date date : fechas) {
			String fechasinaño = dt.format(date);
			if(fechasinaño.equals(dt.format(feriado.getFecha()))) {
				return false;
			}
		}
		
		
		
//		Iterable<Feriado> feriados = feriadorepository.findporAmbito();
//		List<Date> fechas = new ArrayList<Date>();
//
//		if (feriados == null) {
//			return true;
//		}
//
//		for (Feriado feriado : feriados) {
//			// Logger.info("TE MUESTRA ESTO : " + "asd2132121asdasasddass");
//
//			//Ambito ambito = feriado.getAmbito();
//			// Logger.info("TE MUESTRA ESTO : " + "asd2132121asdasasddas"+ ambito.getId());
//
////			if (ambito.getId() == id) {
////				// Logger.info("TE MUESTRA ESTO : " + "asd2132121asdasa1111111");
////				fechas.add(feriado.getFecha());
////			}
//
//		}
//
//		fechas.add(fecha);
//		// Logger.info("TE MUESTRA ESTO : " + "asddasda99999999999");
//
//		for(Date fechaas : fechas) {
//			Logger.info("ENTRO A REGISTRAR123 " + "JORGE");
//		}
//		Logger.info("ENTRO A REGISTRAR123 " );
//		HashMap codigosfechas = new HashMap();
//		for (Date dates : fechas) {
//			codigosfechas.put(dates, 0);
//		}
//
//		if (codigosfechas.size() != fechas.size()) {
//			return false;
//		}
//		return true;
		Logger.info("ASDADDASDA");

		return true;
	}

	public boolean validarFecha(String fecha) {
		try {
			SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
			formatoFecha.setLenient(false);
			formatoFecha.parse(fecha);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public boolean malformato(String fecha) {

		String max = fecha.substring(5);
		if (!max.equals("")) {
			return false;
		}

		return true;
	}

	public boolean validarferiado(Feriado feriadopermanente) {

		boolean constante = true;

		if (feriadopermanente.getFecha().equals("")) {
			constante = false;
		}

		if (feriadopermanente.getNombre().equals("")) {
			constante = false;
		}
		return constante;
	}

	@Override
	public RestResponse eliminar(Map<String, String> pathVariablesMap) {
		Feriado feriado;
		try {
			if (pathVariablesMap.containsKey("id")) {
				Logger.info("TE MUESTRA ESTO : " + pathVariablesMap.get("id"));
				Long id = Long.parseLong(pathVariablesMap.get("id"));
				feriado = feriadorepository.findById(id).orElse(null);
				Logger.info("ENTRO A SERVICE");
				
				if(feriado==null) {
					Logger.info("ENTRO A SERVICE");
					return new RestResponse(HttpStatus.BAD_REQUEST.value(),
							"El feriado No se encuentra");						
				}

				if(feriado.getTipoPeriodo().getId()==EstadoTipoPeriodo.PERIODO_AÑO) {
					if(feriado.getFecha().compareTo(date)<0) {
						return new RestResponse(HttpStatus.BAD_REQUEST.value(),
								"El feriado ingresado ya pasó");					
					}					
				}
				Logger.info("ENTRO A CONTROLLER");

				feriadorepository.deleteById(id);
				return new RestResponse(HttpStatus.OK.value(), "Se ha eliminado el feriado permanente del sistema");

			} else {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(), "No se ha encontrado el feriado a eliminar");
			}
		} catch (NumberFormatException ex) { // handle your exception
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), "No se ha encontrado el feriado a eliminar");
		}

	}

	@Override
	public RestResponse registrarferiado(String feriado, Map<String, String> pathVariablesMap) throws IOException {
		if (pathVariablesMap.containsKey("id")) {
			Long id = Long.parseLong(pathVariablesMap.get("id"));
			this.mapper = new ObjectMapper();
			mapper.isEnabled(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
			modelFeriado modelferiados;
			Logger.info("ENTRO A SERVICE");
			try {
				modelferiados = this.mapper.readValue(feriado, modelFeriado.class);
			} catch (Exception e) {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Verifique los datos ingresados");
			}

			try {
				mapper.reader(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY).readTree(feriado);
			} catch (JsonMappingException e) {
				return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Mal Formato de Json");
			}

			Region ambito = regionrepository.findById(id).orElse(null);
			if (ambito == null) {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(),
						"No se ha encontrado el Id - Verifique los datos");
			}
			Logger.info("ENTRO A SERVICE2");

			// necesito el Ambito ambito, modeloferiadopermanente feriadopermanente
			//modelferiados.setAmbito(ambito);
			
			if(modelferiados.getTipoperiodo().getId()==EstadoTipoPeriodo.PERIODO_PERMANENTE) {
				Logger.info("ENTRO A SERVICE3");
				return registrarferiadopermanente(modelferiados);
			}else {
				return registrarferiadoaño(modelferiados);
			}	
			
		}else {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), "INGRESE LA ID DEL AMBITO");
		}

	}
	

	@Override
	public RestResponse guardarrferiado(String feriado) throws IOException {
			this.mapper = new ObjectMapper();
			Logger.info("ENTRO A SERVICE3");
			mapper.isEnabled(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
			modelFeriado modelferiados;
			try {
				Logger.info("ENTRO A SERVICE3");
				Logger.info("ENTRO A SERVICE+");

				modelferiados = this.mapper.readValue(feriado, modelFeriado.class);
			} catch (Exception e) {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Verifique los datos ingresadoss");
			}
			
			Feriado feriadoss = feriadorepository.findbyNombre(modelferiados.getNombre());
			
			if(feriadoss!=null) {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(), "El nombre ya se encuentra registrado");
			}
			
			try {
				mapper.reader(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY).readTree(feriado);
			} catch (JsonMappingException e) {
				return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Mal Formato de Json");
			}
			Logger.info("ENTRO A SERVICE3");

			if(modelferiados.getTipoperiodo().getId()==EstadoTipoPeriodo.PERIODO_PERMANENTE) {
				String fecha2 = modelferiados.getFecha();
				modelferiados.setFecha(fecha2.substring(0,5));
				Logger.info(modelferiados.getFecha());
				return registrarferiadopermanente(modelferiados);
			}else {
				return registrarferiadoaño(modelferiados);
			}	
	}
	
	
	public RestResponse registrarferiadoaño(modelFeriado modelferiados) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		Logger.info("ENTROOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO " );

				Date dateI = null;
				try {
					try {
						dateI = dateFormat.parse(modelferiados.getFecha());
					} catch (Exception e) {
						return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
					}

					if (!validarfecha(modelferiados.getFecha())) {
						return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
					}
					Logger.info("ENTRO A REGISTRAR ========== "+ modelferiados.getFecha());
					Feriado feriado = new Feriado();

					try {
						feriado = convertirpermanentete(modelferiados);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Logger.info("ENTRO A REGISTRAR ========== "+ modelferiados.getFecha());

					if (!validarferiado(feriado)) {
						Logger.info("ENTRO A REGISTRAR2");
						Logger.info("ENTRO A REGISTRAR ========== "+ modelferiados.getFecha());
						return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
					} else {
						Logger.info("ENTRO A REGISTRARR ========== "+ feriado.getFecha());						
						if (compararfechas(feriado.getFecha())) {
							Logger.info("ENTRO A REGISTRAR5 ");

							if (repetirfechaaño(feriado)) {
								Logger.info("ENTRO A REGISTRAR3");
								
								if(comprobarconpermanente(feriado)) {
									Logger.info("ENTRO A REGISTRAR34848");
									
									feriadorepository.save(feriado);
									return new RestResponse(HttpStatus.OK.value(),"Se ha registrado correctamente los datos del ámbito");
								}else {
									Logger.info("ENTRO A REGISTRAR4");

									return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
		
								}
							} else {
								return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Repites el feriado");
							}

						} else {
							Logger.info("ENTRO A REGISTRAR8");

							return new RestResponse(HttpStatus.BAD_REQUEST.value(), "INGRESE UNA FECHA SUPERIOR A LA ACTUAL");
						}
						// DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
						// Logger.info("Año Completo " + date);
					}
				} catch (

				NullPointerException e) {
					Logger.info("ENTRO A REGISTRAR10");

					return new RestResponse(HttpStatus.BAD_REQUEST.value(), MENSAJE_VERIFICAR);
				}
	}
	

	public boolean comprobarconpermanente(/*Date fecha,Long id*/ Feriado feriadoaño) {
		/*
		boolean constante = true;
		DateFormat dateFormat = new SimpleDateFormat("dd-MM");
		DateFormat dateFormataño = new SimpleDateFormat("yyyy");
		String dia = dateFormat.format(feriadoaño.getFecha());
		Iterable<Feriado> feriados = feriadorepository.findAll();
		List<String> fechas = new ArrayList<String>();
		 */
		
		Iterable<Date> fechas2 = null;
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM");				
		for(Region ambito : feriadoaño.getRegiones()) {
			fechas2 = feriadorepository.findporAmbito(ambito.getId());
		}
		
		for(Date date : fechas2) {
			String fechasinaño = dt.format(date);
			if(fechasinaño.equals(dt.format(feriadoaño.getFecha()))) {
				return false;
			}
		}
		
		
//		if(feriados==null) {
//			return true;
//		}
//		
//		
//		for (Feriado feriado : feriados) {
			//Ambito ambito = feriado.getAmbito();
//			String Stringaño = dateFormataño.format(feriado.getFecha());
//			String Stringdiames = dateFormat.format(feriado.getFecha());
			
//			if(ambito.getId()==id) {
//				if(Stringaño.equals("1970") || Stringaño.equals("1980")) {
//					fechas.add(Stringdiames);
//				}
//			}
//			
//		}
//		fechas.add(dia);
//		HashMap codigosfechas = new HashMap();
//		for (String dates : fechas) {
//			codigosfechas.put(dates, 0);
//		}
//
//		if (codigosfechas.size() != fechas.size()) {
//			return false;
//		}
		return true;
	}
/*
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
	*/

	public boolean repetirfechaaño(Feriado feriado) {
		//año
		/*
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
		*/
		
		
		Iterable<Date> fechas = null;
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
		Logger.info("++++++");
		for(Region ambito : feriado.getRegiones() ) {
			Logger.info("IDS "+ ambito.getId());
			fechas = feriadorepository.feriadoañofindporAmbito(ambito.getId());
		}
		Logger.info("++++++54564");
		
		for(Date date : fechas) {
			String fechasinaño = dt.format(date);
			if(fechasinaño.equals(dt.format(feriado.getFecha()))) {
				return false;
			}
		}
		
		
		
//		Iterable<Feriado> feriados = feriadorepository.findporAmbito();
//		List<Date> fechas = new ArrayList<Date>();
//
//		if (feriados == null) {
//			return true;
//		}
//
//		for (Feriado feriado : feriados) {
//			// Logger.info("TE MUESTRA ESTO : " + "asd2132121asdasasddass");
//
//			//Ambito ambito = feriado.getAmbito();
//			// Logger.info("TE MUESTRA ESTO : " + "asd2132121asdasasddas"+ ambito.getId());
//
////			if (ambito.getId() == id) {
////				// Logger.info("TE MUESTRA ESTO : " + "asd2132121asdasa1111111");
////				fechas.add(feriado.getFecha());
////			}
//
//		}
//
//		fechas.add(fecha);
//		// Logger.info("TE MUESTRA ESTO : " + "asddasda99999999999");
//
//		for(Date fechaas : fechas) {
//			Logger.info("ENTRO A REGISTRAR123 " + "JORGE");
//		}
//		Logger.info("ENTRO A REGISTRAR123 " );
//		HashMap codigosfechas = new HashMap();
//		for (Date dates : fechas) {
//			codigosfechas.put(dates, 0);
//		}
//
//		if (codigosfechas.size() != fechas.size()) {
//			return false;
//		}
//		return true;
		return true;
	}

	public boolean compararfechas(Date fecha) {
		//DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		//SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Logger.info("ENTRO A REGISTRAR99");
		Logger.info("ENTRO A REGISTRAR ========== "+ fecha);
		
		String dia = dateFormat.format(date);
		Date dates = null;
		try {
			dates = dateFormat.parse(dia);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Logger.info(" "+dates);
		Logger.info(" "+fecha);
		
		if (fecha.compareTo(dates) > 0) {
			Logger.info("ENTRO A REGISTRAR100");			
			return true;
		}
		
		return false;
	}

	public boolean validarfecha(String fecha) {
		Logger.info("ENTRO A REGISTRAR555");

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


/*
	public boolean validarferiado(Feriado feriadoAño) {

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
	
	*/
	public RestResponse registrarferiadopermanente(modelFeriado modelferiados) {
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM");
		Date dateI = null;
		Date dateF = null;
		try {
			try {
				dateI = dt.parse(modelferiados.getFecha());
				dateF = dt.parse(modelferiados.getFecha());
			} catch (Exception e) {
				Logger.info("ENTRO A SERVICE4");
				return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Verifique los datos ingresados");
			}

			if (!malformato(modelferiados.getFecha())) {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(),
						"Verifique los datos ingresadossssssssssssssssaa");
			}
			Logger.info("ENTRO A SERVICE5");

			modelferiados.setFecha(completar(modelferiados.getFecha()));
			if (!validarFecha(modelferiados.getFecha())) {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Verifique los datos ingresadoss");
			}
			
			Feriado feriado = new Feriado();

			try {
				feriado = convertir(modelferiados);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Logger.info("ENTRO A SERVICE5");			
			if (!validarferiado(feriado)) {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Verifique los datos ingresados");
			} else {
				Logger.info("ENTRO A SERVICE58");

				if ( repetirfecha(feriado)) {
					Logger.info("ENTRO A SERVICE556");

					feriadorepository.save(feriado);
					return new RestResponse(HttpStatus.OK.value(),
							"Se ha registrado correctamente los datos del ámbito");
				} else {
					return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Repites el feriado");
				}
			}
		} catch (NullPointerException e) {
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Verifique los datos ingresadosssssa	");
		}
	}

	@Override
	public Iterable<modelFeriado> listarferiados() {
		Iterable<Feriado> feriados = feriadorepository.findAll();
		List<modelFeriado> listadoferiados = convertir(feriados);
		return listadoferiados;
	}
	
	public List<modelFeriado> convertir(Iterable<Feriado> feriados){
		List<modelFeriado> feriadoss =new ArrayList<>();
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM");		
		for(Feriado feriado : feriados) {
			modelFeriado modelferiado = new modelFeriado();
			modelferiado.setId(feriado.getId());
			modelferiado.setRegiones(feriado.getRegiones());
			if(/*feriado.getPeriodo()==1*/   feriado.getTipoPeriodo().getId()!=EstadoTipoPeriodo.PERIODO_AÑO) {
				modelferiado.setFecha( formato.format(feriado.getFecha()));
			}else{
				modelferiado.setFecha( dateFormat.format(feriado.getFecha()));
			}
			//modelferiado.setPeriodo(feriado.getTipoperiodo().getId());
			modelferiado.setTipoperiodo(convertirmodeltipo(feriado.getTipoPeriodo())); 
			modelferiado.setNombre(feriado.getNombre());
			feriadoss.add(modelferiado);
		}
		return feriadoss;
		
	}

	@Override
	public Iterable<Date> listarfechas() {
		return feriadorepository.findporAmbito(1L);
	}


}

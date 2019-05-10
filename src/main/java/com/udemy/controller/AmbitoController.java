package com.udemy.controller;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.entity.Ambito;
import com.udemy.entity.Dia;
import com.udemy.entity.DiaHora;
import com.udemy.entity.Feriado;
import com.udemy.entity.SubAmbito;
import com.udemy.model.modelAmbito;
import com.udemy.model.modelFeriado;
import com.udemy.service.interfaces.IAmbitoService;
import com.udemy.service.interfaces.IDiaService;
import com.udemy.service.interfaces.IFeriadoSer;
import com.udemy.service.interfaces.IFeriadoService;
import com.udemy.service.interfaces.ISubAmbitoService;
import com.udemy.util.RestResponse;

@RestController
@RequestMapping("/ambitos")
public class AmbitoController {
	Map<String, Object> respuesta = new HashMap<String, Object>();

	private static final String NOINGRESADO = "Ingrese el ID del ámbito";
	private static final String MALFORMATO = "Ingrese Formato correcto";
	private static final String NOEXISTE = "El ámbito ingresado no existe";
	private static final String FECHAINCORRECTA = "Ingrese el formato correcto de fecha (DD-MM-YYYY)";
	private static final String SINFECHA = "Ingrese las fechas requeridas";
	private static final String DUPLICADOS = "Datos duplicados";
	private static final String FINALINICIO = "La fecha inicial ingresada es mayor a la fecha final";

	String rpta = "";

	protected ObjectMapper mapper;
	@Autowired
	private IAmbitoService ambitoservice;

	@Autowired
	ISubAmbitoService subambitoService;
	
	@Autowired
	private IDiaService diaservice;

	@Autowired
	private IFeriadoService feriados;

	@Autowired
	private IFeriadoSer feriados2;

	private static final Log Logger = LogFactory.getLog(AmbitoController.class);
	@Autowired
	private IFeriadoSer feriado;

	
	
	@PostMapping
	public RestResponse guardarambito(@RequestBody String ambito)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return ambitoservice.Registrar(ambito);
	}
	
	@GetMapping("/{id}/subambitos")
	public ResponseEntity<Iterable<SubAmbito>> listarSubAmbitosActivosByAmbitoId(@PathVariable Long id){
		return new ResponseEntity<Iterable<SubAmbito>>(subambitoService.listarSubAmbitosActivosByAmbitoId(id) , HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RestResponse> actualizar(@PathVariable Long id, @RequestBody String ambito)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		
		RestResponse response =	ambitoservice.Actualizar(ambito, id);
		if(response.getResponsecode()==HttpStatus.BAD_REQUEST.value()) {
			return new ResponseEntity<RestResponse>(response,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<RestResponse>(response,HttpStatus.OK);
	}
	
	@PutMapping()
	public RestResponse actualizarvarios(@PathVariable Long id, @RequestBody String ambito)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return ambitoservice.Actualizar(ambito, id);
	}
	
	

	@PostMapping({ "/{id}/feriadospermanentes", "/feriadospermanentes" })
	public RestResponse guardarferiadopermanente(@RequestBody String feriadopermanente,
			/* @PathVariable Long id */ @PathVariable Map<String, String> pathVariablesMap)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return feriados.registrar(feriadopermanente, pathVariablesMap);
	}

	@PostMapping({ "/{id}/feriadoanios", "/feriadoanios" })
	public RestResponse guardarferiadoaño(@RequestBody String feriadopermanente,
			/* @PathVariable Long id */ @PathVariable Map<String, String> pathVariablesMap)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return feriados.registrar(feriadopermanente, pathVariablesMap);
	}
	

	@PostMapping({ "/{id}/feriados", "/feriados" })
	public RestResponse guardarferiado(@RequestBody String feriado,
			/* @PathVariable Long id */ @PathVariable Map<String, String> pathVariablesMap)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		Logger.info("ENTRO CONTROLLER");
		return feriados.registrarferiado(feriado, pathVariablesMap);
	}
	 
	

	@GetMapping("/{id}/feriados")
	public ResponseEntity<Iterable<modelFeriado>> listarferiados(@PathVariable Long id)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
			return new ResponseEntity<Iterable<modelFeriado>>(feriados2.listarferiados(id), HttpStatus.OK);
	}	
	
	
	
	
	@GetMapping("/modelambitos")
	public ResponseEntity<Iterable<Ambito>> MostrarAmbito() throws JsonProcessingException {
		return new ResponseEntity<Iterable<Ambito>>(ambitoservice.ListarAmbitos(), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<modelAmbito>> MostrarModalAmbito()  {
		Logger.info("ENTRO CONTROLLER");
		return new ResponseEntity<List<modelAmbito>>(ambitoservice.ListarModalAmbitos(), HttpStatus.OK);
	}	

	
	@GetMapping( { "/{id}/horaslaborales", "/horalaborales" })
	public ResponseEntity<?>  MostrarAmbitoporAmbito(@PathVariable Map<String, String> pathVariablesMap) throws JsonProcessingException {
		try {
			if (pathVariablesMap.containsKey("id")) {
				Long id = Long.parseLong(pathVariablesMap.get("id"));
				Ambito ambito = ambitoservice.ListarById(id);
				if (ambito == null) {
					respuesta.put("mensaje", NOEXISTE);
					return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<Iterable<DiaHora>>(ambitoservice.listarhoras(id), HttpStatus.OK);
			} else {
				respuesta.put("mensaje", NOINGRESADO);
				return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);

			}
		} catch (NumberFormatException ex) {
			respuesta.put("mensaje", MALFORMATO);
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
		}
	
	}
	//return this.ambitoservice.listarhoras(id);

	@GetMapping({ "/{id}/diaslaborales", "/diaslaborales" })
	public ResponseEntity<?> MostrarDias(@PathVariable Map<String, String> pathVariablesMap) {
		try {
			if (pathVariablesMap.containsKey("id")) {
				Long id = Long.parseLong(pathVariablesMap.get("id"));
				Ambito ambito = ambitoservice.ListarById(id);
				if (ambito == null) {
					respuesta.put("mensaje", NOEXISTE);
					return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<Iterable<Dia>>(diaservice.diaslaborales(id), HttpStatus.OK);
			} else {
				respuesta.put("mensaje", NOINGRESADO);
				return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);

			}
		} catch (NumberFormatException ex) {
			respuesta.put("mensaje", MALFORMATO);
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping({ "/{id}/feriadosrangos", "/feriadosrangos" })
	public ResponseEntity<?> BuscarFeridos(@PathVariable Map<String, String> pathVariablesMap,
			@RequestParam(value = "fecha1", required = false) String fecha1,
			@RequestParam(value = "fecha2", required = false) String fecha2) throws ParseException {
		try {
			if (pathVariablesMap.containsKey("id")) {
				Long id = Long.parseLong(pathVariablesMap.get("id"));
				try {
					if (feriado.validar(id, fecha1, fecha2) != 0) {
						switch (feriado.validar(id, fecha1, fecha2)) {
						case 1:
							rpta = SINFECHA;
							break;
						case 2:
							rpta = DUPLICADOS;
							break;
						case 3:
							rpta = FECHAINCORRECTA;
							break;
						case 4:
							rpta = FINALINICIO;
							break;
						case 5:
							rpta = NOEXISTE;
							break;
						}
						respuesta.put("mensaje", rpta);
						return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
					}
				} catch (NullPointerException e) {
					respuesta.put("mensaje", SINFECHA);
					return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);

				}
				return new ResponseEntity<List<Object>>(feriado.Listarferiados(id, fecha1, fecha2), HttpStatus.OK);

			} else {
				respuesta.put("mensaje", NOINGRESADO);
				return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);

			}
		} catch (NumberFormatException ex) {
			respuesta.put("mensaje", MALFORMATO);
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
		}
   
		
		
		
	}

	@GetMapping({ "/{id}/diaslaboralesporrango", "//diaslaboralesporrango" })
	public ResponseEntity<?> listardiaslaborales(@PathVariable Map<String, String> pathVariablesMap,
			@RequestParam(value = "fecha1", required = false) String fecha1,
			@RequestParam(value = "fecha2", required = false) String fecha2)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
			Logger.info("INGRESADO FECHA1 "+fecha1);
			Logger.info("INGRESADO FECHA2 "+fecha2);
			
		try {
			if (pathVariablesMap.containsKey("id")) {
				Long id = Long.parseLong(pathVariablesMap.get("id"));

				try {
					if (feriado.validar(id, fecha1, fecha2) != 0) {
						switch (feriado.validar(id, fecha1, fecha2)) {
						case 1:
							rpta = SINFECHA;
							break;
						case 2:
							rpta = DUPLICADOS;
							break;
						case 3:
							rpta = FECHAINCORRECTA;
							break;
						case 4:
							rpta = FINALINICIO;
							break;
						case 5:
							rpta = NOEXISTE;
							break;
						}
						respuesta.put("mensaje", rpta);
						return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
					}
				} catch (NullPointerException e) {
					respuesta.put("mensaje", SINFECHA);
					return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);

				}
				return new ResponseEntity<HashMap<Integer,String>>(diaservice.listardiaslaborales(id, fecha1, fecha2),
						HttpStatus.OK);
			} else {
				respuesta.put("mensaje", NOINGRESADO);
				return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);

			}
		} catch (NumberFormatException ex) {
			respuesta.put("mensaje", MALFORMATO);
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}/fechalimite")
	public ResponseEntity<Map<String, Object>> listarFechaLimite(@RequestParam(value = "fecha") String fecha, @PathVariable Long id, @RequestParam(value = "dia") int dia) throws ParseException{
		Date fechalimite = 	ambitoservice.listarFechaLimite(fecha, id, dia);
		Map<String, Object> respuesta = new HashMap<String, Object>();
		respuesta.put("fechaLimite", fechalimite);
		return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
	}
	
}

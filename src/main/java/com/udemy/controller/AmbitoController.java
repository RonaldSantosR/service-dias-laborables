package com.udemy.controller;
import java.io.IOException;
import java.text.ParseException;
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
import com.udemy.service.interfaces.IAmbitoService;
import com.udemy.service.interfaces.IDiaService;
import com.udemy.service.interfaces.IFeriadoAñoService;
import com.udemy.service.interfaces.IFeriadoSer;
import com.udemy.service.interfaces.IFeriadoService;
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
	private IDiaService diaservice;

	@Autowired
	private IFeriadoService feriadopermanentes;

	@Autowired
	private IFeriadoAñoService feriadoaño;

	private static final Log Logger = LogFactory.getLog(AmbitoController.class);
	@Autowired
	private IFeriadoSer feriado;

	@PostMapping
	public RestResponse guardarambito(@RequestBody String ambito)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return ambitoservice.Registrar(ambito);
	}

	@PutMapping("/{id}")
	public RestResponse actualizar(@PathVariable Long id, @RequestBody String ambito)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return ambitoservice.Actualizar(ambito, id);
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
		return feriadopermanentes.registrar(feriadopermanente, pathVariablesMap);
	}

	@PostMapping({ "/{id}/feriadoanios", "/feriadoanios" })
	public RestResponse guardarferiadoaño(@RequestBody String feriadopermanente,
			/* @PathVariable Long id */ @PathVariable Map<String, String> pathVariablesMap)
			throws JsonParseException, JsonMappingException, IOException, ParseException {
		return feriadoaño.registrar(feriadopermanente, pathVariablesMap);
	}

	@GetMapping
	public Iterable<Ambito> MostrarAmbito() throws JsonProcessingException {
		return this.ambitoservice.ListarAmbitos();
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

	@GetMapping({ "/{id}/feriados", "/feriados" })
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
				return new ResponseEntity<List<String>>(diaservice.listardiaslaborales(id, fecha1, fecha2),
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
}

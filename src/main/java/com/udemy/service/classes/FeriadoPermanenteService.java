package com.udemy.service.classes;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.entity.Ambito;
import com.udemy.entity.Feriado;
import com.udemy.entity.FeriadoPermanente;
import com.udemy.model.modelferiadopermanente;
import com.udemy.repository.AmbitoRepository;
import com.udemy.repository.FeriadoPRepository;
import com.udemy.repository.FeriadoRepository;
import com.udemy.service.interfaces.IFeriadoService;
import com.udemy.util.RestResponse;

@Service
public class FeriadoPermanenteService implements IFeriadoService {

	private static final Log Logger = LogFactory.getLog(FeriadoPermanenteService.class);
	protected ObjectMapper mapper;

	@Autowired
	private FeriadoPRepository feriadoprepository;
	@Autowired
	private FeriadoRepository feriadorepository;
	@Autowired
	private AmbitoRepository ambitorepository;

	@Override
	public RestResponse registrar(String feriadopermanentes, Map<String, String> pathVariablesMap) throws IOException {

		if (pathVariablesMap.containsKey("id")) {
			Long id = Long.parseLong(pathVariablesMap.get("id"));
			this.mapper = new ObjectMapper();
			mapper.isEnabled(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
			modelferiadopermanente feriadopermanentess;
			try {
				feriadopermanentess = this.mapper.readValue(feriadopermanentes, modelferiadopermanente.class);
			} catch (Exception e) {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Verifique los datos ingresados");
			}

			try {
				mapper.reader(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY).readTree(feriadopermanentes);
			} catch (JsonMappingException e) {
				return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Mal Formato de Json");
			}

			Ambito ambito = ambitorepository.findById(id).orElse(null);
			if (ambito == null) {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(),
						"No se ha encontrado el Id - Verifique los datos");
			}

			// necesito el Ambito ambito, modeloferiadopermanente feriadopermanente
			feriadopermanentess.setAmbito(ambito);
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
				FeriadoPermanente feriado = new FeriadoPermanente();

				try {
					feriado = convertir(feriadopermanentess);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (!validarferiado(feriado)) {
					return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Verifique los datos ingresados");
				} else {

					if (repetirfecha(feriado.getFecha(), id)) {
						feriadoprepository.save(feriado);
						return new RestResponse(HttpStatus.OK.value(),
								"Se ha registrado correctamente los datos del ámbito");
					} else {
						return new RestResponse(HttpStatus.BAD_REQUEST.value(), "Repites el feriado");
					}
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

	public FeriadoPermanente convertir(modelferiadopermanente modelfe) throws ParseException {

		FeriadoPermanente per = new FeriadoPermanente();

		per.setAmbito(modelfe.getAmbito());
		per.setNombre(modelfe.getNombre());

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM");
		Date date = formatter.parse(modelfe.getFecha());
		per.setFecha(date);

		return per;

	}

	public boolean repetirfecha(Date fecha, Long id) {

		int i = 0;

		Iterable<Feriado> feriados = feriadorepository.findAll();
		List<Date> fechas = new ArrayList<Date>();

		if (feriados == null) {
			return true;
		}

		for (Feriado feriado : feriados) {
			// Logger.info("TE MUESTRA ESTO : " + "asd2132121asdasasddass");

			Ambito ambito = feriado.getAmbito();
			// Logger.info("TE MUESTRA ESTO : " + "asd2132121asdasasddas"+ ambito.getId());

			if (ambito.getId() == id) {
				// Logger.info("TE MUESTRA ESTO : " + "asd2132121asdasa1111111");
				fechas.add(feriado.getFecha());
			}

		}

		fechas.add(fecha);
		// Logger.info("TE MUESTRA ESTO : " + "asddasda99999999999");

		HashMap codigosfechas = new HashMap();
		for (Date dates : fechas) {
			codigosfechas.put(dates, 0);
		}

		if (codigosfechas.size() != fechas.size()) {
			return false;
		}
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

	public boolean validarferiado(FeriadoPermanente feriadopermanente) {

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
		Feriado feriadopermanente;
		try {
			if (pathVariablesMap.containsKey("id")) {
				Logger.info("TE MUESTRA ESTO : " + pathVariablesMap.get("id"));
				Long id = Long.parseLong(pathVariablesMap.get("id"));
				feriadopermanente = feriadoprepository.findById(id).orElse(null);

				if (feriadopermanente == null) {
					return new RestResponse(HttpStatus.BAD_REQUEST.value(),
							"No se ha encontrado el feriado a eliminar");
				}

				feriadoprepository.deleteById(id);
				return new RestResponse(HttpStatus.OK.value(), "Se ha eliminado el feriado permanente del sistema");

			} else {
				return new RestResponse(HttpStatus.BAD_REQUEST.value(), "No se ha encontrado el feriado a eliminar");
			}
		} catch (NumberFormatException ex) { // handle your exception
			return new RestResponse(HttpStatus.BAD_REQUEST.value(), "No se ha encontrado el feriado a eliminar");
		}

	}

}

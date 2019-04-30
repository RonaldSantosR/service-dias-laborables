package com.udemy.service.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.udemy.entity.Ambito;
import com.udemy.entity.Dia;
import com.udemy.entity.Feriado;
import com.udemy.model.modelFeriado;
import com.udemy.repository.AmbitoRepository;
import com.udemy.repository.DiaRepository;
import com.udemy.repository.FeriadoRepository;
import com.udemy.service.interfaces.IDiaService;
import com.udemy.util.RestResponse;

@Service
public class DiaService implements IDiaService {

	@Autowired
	private DiaRepository diaRepository;

	@Autowired
	private AmbitoRepository ambitorepo;

	@Autowired
	private FeriadoRepository feriadorepo;


	@Override
	public List<Dia> findall() {
		return diaRepository.findAll();
	}

	@Override
	public Iterable<Dia> diaslaborales(Long id) {
		return diaRepository.findByDiaPorAmbito(id);
	}

	@Override
	public List<String> listardiaslaborales(Long id, String fecha1, String fecha2) throws ParseException {

		Ambito ambito = ambitorepo.findById(id).orElse(null);

		if (ambito == null) {
			return null;
		}

		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
		Date fechauno = null;
		Date fechados = null;

		fechauno = formatoDelTexto.parse(fecha1);
		fechados = formatoDelTexto.parse(fecha2);

		return Listardias(id, fechauno, fechados);
	}

	public List<modelFeriado> quitaraño(Long id) {
		Iterable<Feriado> feriadospermanentes = feriadorepo.findAllByAmbitoid(id);
		
		List<Date> dates2 = new ArrayList<>();
		List<modelFeriado> modelferiadospermentes = new ArrayList<>();

		for (Feriado feriadop : feriadospermanentes) {
			modelFeriado model = converterferiados(feriadop);
			modelferiadospermentes.add(model);
		}

		return modelferiadospermentes;
	}

	public modelFeriado converterferiados(Feriado feriados) {
		modelFeriado modelferiados = new modelFeriado();
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM");

		modelferiados.setId(feriados.getId());
		modelferiados.setNombre(feriados.getNombre());
		modelferiados.setFecha(formatoDelTexto.format(feriados.getFecha()));
		return modelferiados;
	}

	public List<Date> getListaEntreFechas2(Date fechaInicio, Date fechaFin) {

		Calendar c1 = Calendar.getInstance();
		c1.setTime(fechaInicio);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(fechaFin);

		java.util.List<Date> listaFechas = new java.util.ArrayList<Date>();

		while (!c1.after(c2)) {
			listaFechas.add(c1.getTime());
			c1.add(Calendar.YEAR, 1);
		}
		return listaFechas;
	}

	public modelFeriado transformarfecha(modelFeriado model, Date dia) {
		String fecha2 = "";
		modelFeriado mo = new modelFeriado();
		SimpleDateFormat formato2 = new SimpleDateFormat("yyyy");
		// Logger.info("ENTROOOOOOOOOOOOOOOOOOO
		// "+fecha.concat("-").concat(formato2.format(dia)));
		mo.setId(model.getId());
		mo.setNombre(model.getNombre());
		mo.setFecha(model.getFecha().concat("-").concat(formato2.format(dia)));
		return mo;
	}

	public List<String> Listardias(Long id, Date fechauno, Date fechados) throws ParseException {
		List<Date> listaEntreFechas = getListaEntreFechas2(fechauno, fechados);
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		Iterable<Dia> diasambito = diaRepository.findByDiaPorAmbito(id);
		List<String> nombredias = new ArrayList<>();
		List<modelFeriado> modelo = new ArrayList<>();
		List<modelFeriado> models = quitaraño(id);
		Iterable<Feriado> feriados = feriadorepo.findAllByAmbitoid(id, fechauno, fechados);
		List<Date> listaentrefecha = getListaEntreFechas(fechauno, fechados);
		SimpleDateFormat sdff = new SimpleDateFormat("E");
		List<String> devolverfechas = new ArrayList<>();
		//List<FeriadoPermanente> feriadoperm = new ArrayList<>();
		for (modelFeriado feriadopermanente : models) {
			for (Date dia : listaEntreFechas) {
				modelFeriado feriadoper = transformarfecha(feriadopermanente, dia);
				modelo.add(feriadoper);
				// dates2.add(formato.parse(diaa.concat("-").concat(formato2.format(dia))));
			}

		}

		/*
		 * for(modelferiadopermanente modelll : modelo) {
		 * feriadoperm.add(converterferiadosentity(modelll)); }
		 */

		for (Dia day : diasambito) {
			nombredias.add(day.getNombre().substring(0, 3).toLowerCase());
		}

		for (Date date : listaentrefecha) {
			boolean constante = true;
			for (Feriado fecha : feriados) {
				if (formato.format(date).equals(formato.format(fecha.getFecha()))) {
					constante = false;
				}
			}

			for (modelFeriado fecha2 : modelo) {// FeriadoPermanente fecha2 : feriadoperm
				if (formato.format(date).equals(fecha2.getFecha())) {// formato.format(fecha2.getFecha()))
					constante = false;
				}
			}

			for (String diass : nombredias) {
				if (sdff.format(date).equals(diass) && constante) {
					devolverfechas.add(formato.format(date));
				}
			}

		}

		return devolverfechas;
	}

	/*
	 * public FeriadoPermanente converterferiadosentity(modelferiadopermanente
	 * feriados) throws ParseException{ FeriadoPermanente modelferiados=new
	 * FeriadoPermanente(); SimpleDateFormat formatoDelTexto = new
	 * SimpleDateFormat("dd-MM-yyyy");
	 * 
	 * modelferiados.setId(feriados.getId());
	 * modelferiados.setNombre(feriados.getNombre());
	 * modelferiados.setFecha(formatoDelTexto.parse(feriados.getFecha())); return
	 * modelferiados; }
	 */

	public List<Date> getListaEntreFechas(Date fechaInicio, Date fechaFin) {

		Calendar c1 = Calendar.getInstance();
		c1.setTime(fechaInicio);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(fechaFin);
		java.util.List<Date> listaFechas = new java.util.ArrayList<Date>();

		while (!c1.after(c2)) {
			listaFechas.add(c1.getTime());
			c1.add(Calendar.DAY_OF_MONTH, 1);
		}
		return listaFechas;
	}

}

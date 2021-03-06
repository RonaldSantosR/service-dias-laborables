package com.udemy.service.classes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.SortedMap;
import java.util.TreeMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.entity.Region;
import com.udemy.entity.Feriado;
import com.udemy.entity.TipoPeriodo;
import com.udemy.model.modelRegion;
import com.udemy.model.modelFeriado;
import com.udemy.model.modeltipoPeriodo;
import com.udemy.repository.RegionRepository;
import com.udemy.repository.FeriadoRepository;
import com.udemy.service.interfaces.IFeriadoSer;

@Service
public class FeriadoService implements IFeriadoSer {

	private static final Log Logger = LogFactory.getLog(FeriadoService.class);

	
	@Autowired
	private FeriadoRepository feriadorepo;
	
	
	@Autowired
	private RegionRepository regionrepo;
	
	@Override
	public List<Object> Listarferiados(Long id,String fecha1,String fecha2) throws ParseException {
		
		Region ambito = regionrepo .findById(id).orElse(null); 
		
		if(ambito==null) {
			return null;
		}
		
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
		Date fechauno = null;
		Date fechados = null;
		
		fechauno = formatoDelTexto.parse(fecha1);
		fechados = formatoDelTexto.parse(fecha2);

		return Listarferiados(id,fechauno,fechados);
		
	}
	
	public modelFeriado converterferiados(Feriado feriados){
		modelFeriado modelferiados=new modelFeriado();
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM");
		SimpleDateFormat formatoDelTexto2 = new SimpleDateFormat("dd-MM-yyyy");
		
		modelferiados.setId(feriados.getId());
		modelferiados.setNombre(feriados.getNombre());
		
		if(feriados.getTipoPeriodo().getId() ==1) {
			modelferiados.setFecha(formatoDelTexto.format(feriados.getFecha()));
		}else {
			modelferiados.setFecha(formatoDelTexto2.format(feriados.getFecha()));		
		}
		modelferiados.setRegiones(feriados.getRegiones() );
        Set<Region> ambitos = new HashSet<Region>();		
		for(Region ambito : feriados.getRegiones()) {
			ambitos.add(ambito);
		}
		modelferiados.setRegiones(ambitos);
		//modelferiados.setPeriodo(feriados.getPeriodos());
		modelferiados.setTipoperiodo(convertirmodeltipo(feriados.getTipoPeriodo()));
		return 	modelferiados;
	}
	
	public modelRegion converteramodel(Region ambito) {
		modelRegion modelambito = new modelRegion();
		modelambito.setId(ambito.getId());
		modelambito.setNombre(ambito.getNombre());
		return modelambito;
	}
	
	
	public Feriado converterferiadosentity(modelFeriado feriados) throws ParseException{
		Feriado modelferiados=new Feriado();
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");

		modelferiados.setId(feriados.getId());
		modelferiados.setNombre(feriados.getNombre());
		modelferiados.setFecha(formatoDelTexto.parse(feriados.getFecha()));
		modelferiados.setRegiones(feriados.getRegiones());
		//modelferiados.setPeriodo(feriados.getPeriodos());
		modelferiados.setTipoPeriodo(convertirtipo(feriados.getTipoperiodo()));
		Logger.info("ENTROOOOOO-- " + feriados.getId());

		Logger.info("ENTROOOOOO-- " + modelferiados.getFecha());
		return 	modelferiados;
	}	
	
	
	
	
	
	public List<modelFeriado> quitaraño(Long id){
		
		Iterable<Feriado> feriadospermanentes = feriadorepo.findAllByAmbitoid(id);
		
		List<Date> dates2 = new ArrayList<>();
		List<modelFeriado> modelferiadospermentes = new ArrayList<>();

		for(Feriado feriadop  :feriadospermanentes) {
			
			modelFeriado model = converterferiados(feriadop); 
			
			modelferiadospermentes.add(model);
		}
		
		return modelferiadospermentes;
	}
	
	public List<Feriado> listarferiadosaños(Long id,Date fecha1,Date fecha2){
		Iterable <Feriado> feriadoaño = feriadorepo.findAllByAmbitoidentrefechasaño(id,fecha1,fecha2);
		
		List<Feriado> Feriadoaños = new ArrayList<>();
		
		for(Feriado años : feriadoaño ) {
			Feriadoaños.add(años);
		}
		return Feriadoaños;
	}
	
	
	public modelFeriado listarferiadoo(Feriado feriado) {
		modelFeriado f =new modelFeriado();
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
		f.setId(feriado.getId());
		f.setNombre(feriado.getNombre());
		f.setFecha(formatoDelTexto.format(feriado.getFecha()));
		f.setRegiones(feriado.getRegiones());
		//f.setPeriodo(feriado.getPeriodos());
		f.setTipoperiodo(convertirmodeltipo(feriado.getTipoPeriodo()));
		return f;
	}
	
	public Feriado listarferiadoFeriado(modelFeriado feriado) throws ParseException {
		Feriado f =new Feriado();
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
		f.setId(feriado.getId());
		f.setNombre(feriado.getNombre());
		f.setFecha(formatoDelTexto.parse(feriado.getFecha()));
		f.setRegiones(feriado.getRegiones());		
		//f.setPeriodo(feriado.getPeriodos());
		f.setTipoPeriodo(convertirtipo(feriado.getTipoperiodo()));
		Logger.info("ENTROOOOOO " +f.getFecha()+"assddasssssssssssssssssssssssssss");					
		return f;
	}
	
	public List<Object> Listarferiados(Long id,Date fechauno, Date fechados) throws ParseException{
		
		List<Date> listaEntreFechas = getListaEntreFechas2(fechauno, fechados);
		SimpleDateFormat formato2 = new SimpleDateFormat("yyyy");		
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM");
		List<Date> dates2 = new ArrayList<>();
		//List<Date> dates3 = listarferiadosaños(id,fechauno,fechados);
		List<modelFeriado> modelferiadospermentes = quitaraño(id);
		List<modelFeriado> modelferiadospermenteslistar = new ArrayList<>();
		List<Feriado> feriadosaños=listarferiadosaños(id,fechauno,fechados);
		List<Feriado> feriadopermanentes = new ArrayList<>();
		List<modelFeriado> feriadopermanentess = new ArrayList<>();
		List<Feriado> feriadoaños = new ArrayList<>();
			for(modelFeriado feriadopermanente : modelferiadospermentes) {
				for(Date año : listaEntreFechas) 
				{	
					modelFeriado feriadoper = transformarfecha(feriadopermanente,año);
					//Logger.info("ENTROOOOOO " +feriadoper.getPeriodos()+"IIIIII");										
					Logger.info("ENTROOOOOO " +feriadoper.getFecha()+"assddas");					
					
					if(formato.parse(feriadoper.getFecha()).compareTo(fechauno)>0 && formato.parse(feriadoper.getFecha()).compareTo(fechados)<0) {
						modelferiadospermenteslistar.add(feriadoper);
					}
					
					
					//dates2.add(formato.parse(diaa.concat("-").concat(formato2.format(dia))));
				}	

			}
				
			for(modelFeriado feriadopermanente: modelferiadospermenteslistar) {
				Logger.info("ENTROOOOOO " +feriadopermanente.getFecha()+" jijiji");					
				feriadopermanentes.add(converterferiadosentity(feriadopermanente));
			}
			
			Collections.sort(feriadopermanentes,(x,y)->x.getFecha().compareTo(y.getFecha()));
			Collections.sort(feriadosaños,(x,y)->x.getFecha().compareTo(y.getFecha()));

			List<Object> objetoss =new ArrayList<>();
			List<Object> objetosss =new ArrayList<>();
			
			for(Feriado feriado : feriadosaños) {
				feriadopermanentess.add(listarferiadoo(feriado));
			}
			
			for(modelFeriado model : feriadopermanentess) {
				feriadoaños.add(listarferiadoFeriado(model));
			}		
			
			SortedMap map = new TreeMap();
		    HashMap<Date,Object> listaProductos2 = new HashMap<Date,Object>();


			for(Feriado fe : feriadopermanentes) {
				for(Feriado feriado : feriadoaños) {
					if(fe.getFecha().compareTo(feriado.getFecha())>0) {
						map.put(feriado.getFecha(), feriado);						
					}
				}
				map.put(fe.getFecha(), fe);
			}
			
			Iterator iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
			Object key = iterator.next();
			//System.out.println("Clave : " + key + " Valor :" + map.get(key));
			objetoss.add(map.get(key));
			}
			
		
		//List<modelFeriado> modelferiadospermentes = quitaraño(id,fechauno,fechados);
		
		
		
		return objetoss;
		
	}
	

	
	public LinkedHashMap<Date, Object> sortHashMapByValues(HashMap<Date, Object> passedMap) {
	    List<Date> mapKeys = new ArrayList<>(passedMap.keySet());
	    List<Object> mapValues = new ArrayList<>(passedMap.values());
	    Collections.sort(mapKeys);
	    
	    for(Date fecha : mapKeys) {
	    	Logger.info(fecha	+ " AdasDDS");
	    }
	    
	    LinkedHashMap<Date, Object> sortedMap = new LinkedHashMap<>();

	    Iterator<Object> valueIt = mapValues.iterator();
	    while (valueIt.hasNext()) {
	        Object val = valueIt.next();
	        Iterator<Date> keyIt = mapKeys.iterator();

	        while (keyIt.hasNext()) {
	        	Date key = keyIt.next();
	            Object comp1 = passedMap.get(key);
	            Object comp2 = val;

	            if (comp1.equals(comp2)) {
	                keyIt.remove();
	                sortedMap.put(key, val);
	                break;
	            }
	        }
	    }
	    return sortedMap;
	}	
	
		
	
	
	
	public void pasarfilto(Feriado feriado) {
		List<Object> objetoss =new ArrayList<>();
		for(Object objeto : objetoss){
			if(objeto!=feriado) {
				objetoss.add(feriado);
			}
		}
		
	}
	
	public modelFeriado transformarfecha(modelFeriado model,Date dia) {
		String fecha2="";
		modelFeriado mo = new modelFeriado();
		SimpleDateFormat formato2 = new SimpleDateFormat("yyyy");	
		mo.setId(model.getId());
		mo.setNombre(model.getNombre());
		mo.setFecha(model.getFecha().concat("-").concat(formato2.format(dia)));
		mo.setRegiones(model.getRegiones());
		//mo.setPeriodo(model.getPeriodos());
		mo.setTipoperiodo(model.getTipoperiodo());
		//Logger.info("ENTROOOOOOOOOO8888"+ model.getPeriodos());		

		return mo;
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
	
	

	@Override
	public boolean compararfechas(String fecha1, String fecha2) throws ParseException {

		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
		Date fechauno = null;
		Date fechados = null;
		
		fechauno = formatoDelTexto.parse(fecha1);
		fechados = formatoDelTexto.parse(fecha2);
		
		if (fechauno.compareTo(fechados) > 0) {
			return true;
		}
		
		return false;
	}


	@Override
	public boolean formatofecha(String fecha1, String fecha2) {

		try {
			SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
			formatoFecha.setLenient(false);
			formatoFecha.parse(fecha1);
			formatoFecha.parse(fecha2);
		} catch (ParseException e) {
			Logger.info("ENTROOOOOO");

			return false;
		}
		
		String max = fecha1.substring(10);
		String max2 = fecha2.substring(10);		
		if(!max.equals("") || !max2.equals("")) {
			return false;
		}
		
		
		return true;
	}

	@Override
	public int validar(Long id, String fecha1, String fecha2) throws ParseException {
		
		if(fecha1.equals("") || fecha2.equals("")) {
			return 1;
		}
		
		if(!fecha2.substring(10).equals("")||!fecha1.substring(10).equals("")) {
			return 2;		
		}
		
		if(!formatofecha(fecha1,fecha2)) {
			return 3;
		}
		
		if(compararfechas(fecha1,fecha2)) {
			return 4;
		}
		
		if(Listarferiados(id,fecha1,fecha2)==null) {
			return 5;	
		}
		
		return 0;
	}

	public modelFeriado feriadoamodel(Feriado feriado) {
		modelFeriado f =new modelFeriado();

		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat formatoDeldiames = new SimpleDateFormat("dd-MM");		
		f.setId(feriado.getId());
		f.setNombre(feriado.getNombre());
		
		if(feriado.getTipoPeriodo().getId()==1) {

			f.setFecha(formatoDeldiames.format(feriado.getFecha()));
		}else {
			f.setFecha(formatoDelTexto.format(feriado.getFecha()));
		}

		f.setRegiones(feriado.getRegiones());

		f.setTipoperiodo(convertirmodeltipo(feriado.getTipoPeriodo()));
		return f;
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
	
	
	@Override
	public Iterable<modelFeriado> listarferiados(Long id) {
		Iterable<Feriado> feriadospermanentes = feriadorepo.findAllByAmbitoid2(id);
		List<modelFeriado> modelferiados = new ArrayList<>();
		modelFeriado modelferiado = new modelFeriado();

		for(Feriado feriado : feriadospermanentes) {
			modelferiados.add(feriadoamodel(feriado));
		}
		return modelferiados;
	}
	
	


}

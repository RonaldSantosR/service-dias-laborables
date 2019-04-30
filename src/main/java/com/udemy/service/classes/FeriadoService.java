package com.udemy.service.classes;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.udemy.controller.AmbitoController;
import com.udemy.entity.Ambito;
import com.udemy.entity.Feriado;
import com.udemy.entity.FeriadoAño;
import com.udemy.entity.FeriadoPermanente;
import com.udemy.model.modelFeriadolistar;
import com.udemy.model.modelferiadoaño;
import com.udemy.model.modelferiadopermanente;
import com.udemy.repository.AmbitoRepository;
import com.udemy.repository.FeriadoARepository;
import com.udemy.repository.FeriadoPRepository;
import com.udemy.repository.FeriadoRepository;
import com.udemy.service.interfaces.IFeriadoSer;

@Service
public class FeriadoService implements IFeriadoSer {

	private static final Log Logger = LogFactory.getLog(FeriadoService.class);

	
	@Autowired
	private FeriadoRepository feriadorepo;

	@Autowired
	private FeriadoPRepository feriadoprepo;

	@Autowired
	private FeriadoARepository feriadoarepo;	
	
	@Autowired
	private AmbitoRepository ambitorepo;
	
	@Override
	public List<Object> Listarferiados(Long id,String fecha1,String fecha2) throws ParseException {
		
		Ambito ambito = ambitorepo.findById(id).orElse(null); 
		
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
	
	public modelferiadopermanente converterferiados(FeriadoPermanente feriados){
		modelferiadopermanente modelferiados=new modelferiadopermanente();
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM");

		modelferiados.setId(feriados.getId());
		modelferiados.setNombre(feriados.getNombre());
		modelferiados.setFecha(formatoDelTexto.format(feriados.getFecha()));
		return 	modelferiados;
	}
	
	public FeriadoPermanente converterferiadosentity(modelferiadopermanente feriados) throws ParseException{
		FeriadoPermanente modelferiados=new FeriadoPermanente();
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");

		modelferiados.setId(feriados.getId());
		modelferiados.setNombre(feriados.getNombre());
		modelferiados.setFecha(formatoDelTexto.parse(feriados.getFecha()));
		Logger.info("ENTROOOOOO " + modelferiados.getFecha());
		return 	modelferiados;
	}	
	
	
	
	
	
	public List<modelferiadopermanente> quitaraño(Long id){
		Iterable<FeriadoPermanente> feriadospermanentes = feriadoprepo.findAllByAmbitoid(id);
		List<Date> dates2 = new ArrayList<>();
		List<modelferiadopermanente> modelferiadospermentes = new ArrayList<>();

		for(FeriadoPermanente feriadop  :feriadospermanentes) {
			modelferiadopermanente model = converterferiados(feriadop); 
			modelferiadospermentes.add(model);
		}
		
		return modelferiadospermentes;
	}
	
	public List<FeriadoAño> listarferiadosaños(Long id,Date fecha1,Date fecha2){
		Iterable <FeriadoAño> feriadoaño = feriadoarepo.findAllByAmbitoid(id,fecha1,fecha2);
		List<FeriadoAño> Feriadoaños = new ArrayList<>();
		for(FeriadoAño años : feriadoaño ) {
			Feriadoaños.add(años);
		}
		return Feriadoaños;
	}
	
	
	public modelferiadoaño listarferiadoo(FeriadoAño feriado) {
		modelferiadoaño f =new modelferiadoaño();
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
		f.setId(feriado.getId());
		f.setNombre(feriado.getNombre());
		f.setFecha(formatoDelTexto.format(feriado.getFecha()));
		f.setAsunto(feriado.getAsunto());
		return f;
	}
	
	public FeriadoAño listarferiadoFeriado(modelferiadoaño feriado) throws ParseException {
		FeriadoAño f =new FeriadoAño();
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
		f.setId(feriado.getId());
		f.setNombre(feriado.getNombre());
		f.setFecha(formatoDelTexto.parse(feriado.getFecha()));
		f.setAsunto(feriado.getAsunto());
		Logger.info("ENTROOOOOO " +f.getFecha()+"assddasssssssssssssssssssssssssss");					
		return f;
	}
	
	public List<Object> Listarferiados(Long id,Date fechauno, Date fechados) throws ParseException{
		List<Date> listaEntreFechas = getListaEntreFechas2(fechauno, fechados);
		SimpleDateFormat formato2 = new SimpleDateFormat("yyyy");		
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM");
		List<Date> dates2 = new ArrayList<>();
		//List<Date> dates3 = listarferiadosaños(id);
		List<modelferiadopermanente> modelferiadospermentes = quitaraño(id);
		List<modelferiadopermanente> modelferiadospermenteslistar = new ArrayList<>();
		List<FeriadoAño> feriadosaños=listarferiadosaños(id,fechauno,fechados);
		List<FeriadoPermanente> feriadopermanentes = new ArrayList<>();
		List<modelferiadoaño> feriadopermanentess = new ArrayList<>();
		List<FeriadoAño> feriadoaños = new ArrayList<>();
		

			for(modelferiadopermanente feriadopermanente : modelferiadospermentes) {
				for(Date dia : listaEntreFechas) 
				{	
					modelferiadopermanente feriadoper = transformarfecha(feriadopermanente,dia);
					
					Logger.info("ENTROOOOOO " +feriadoper.getFecha()+"assddas");					
					
					if(formato.parse(feriadoper.getFecha()).compareTo(fechauno)>0 && formato.parse(feriadoper.getFecha()).compareTo(fechados)<0) {
						modelferiadospermenteslistar.add(feriadoper);
					}
					
					
					//dates2.add(formato.parse(diaa.concat("-").concat(formato2.format(dia))));
				}	

			}
			
			
			
			
			
				
			for(modelferiadopermanente feriadopermanente: modelferiadospermenteslistar) {
				Logger.info("ENTROOOOOO " +feriadopermanente.getFecha()+" jijiji");					
				feriadopermanentes.add(converterferiadosentity(feriadopermanente));
			}
			
			Collections.sort(feriadopermanentes,(x,y)->x.getFecha().compareTo(y.getFecha()));
			Collections.sort(feriadosaños,(x,y)->x.getFecha().compareTo(y.getFecha()));

			List<Object> objetoss =new ArrayList<>();
			List<Object> objetosss =new ArrayList<>();
			
			for(FeriadoAño feriado : feriadosaños) {
				feriadopermanentess.add(listarferiadoo(feriado));
			}
			
			for(modelferiadoaño model : feriadopermanentess) {
				feriadoaños.add(listarferiadoFeriado(model));
			}		
			
			SortedMap map = new TreeMap();
		    HashMap<Date,Object> listaProductos2 = new HashMap<Date,Object>();


			for(FeriadoPermanente fe : feriadopermanentes) {
				for(FeriadoAño feriado : feriadoaños) {
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
	
		
	
	
	
	public void pasarfilto(FeriadoAño feriado) {
		List<Object> objetoss =new ArrayList<>();
		for(Object objeto : objetoss){
			if(objeto!=feriado) {
				objetoss.add(feriado);
			}
		}
		
	}
	
	public modelferiadopermanente transformarfecha(modelferiadopermanente model,Date dia) {
		String fecha2="";
		modelferiadopermanente mo = new modelferiadopermanente();
		SimpleDateFormat formato2 = new SimpleDateFormat("yyyy");	
		//Logger.info("ENTROOOOOOOOOOOOOOOOOOO "+fecha.concat("-").concat(formato2.format(dia)));		
		mo.setId(model.getId());
		mo.setNombre(model.getNombre());
		mo.setFecha(model.getFecha().concat("-").concat(formato2.format(dia)));
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
	
	


}

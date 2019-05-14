package com.udemy.model;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.udemy.entity.Region;
import com.udemy.entity.TipoPeriodo;

public class modelFeriado {
	
	private Long id;
	
	private String fecha;
	
	private Set<Region> regiones;
	
	private String nombre;
	
	
	private modeltipoPeriodo tipoperiodo;

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

		

	
	public Set<Region> getRegiones() {
		return regiones;
	}
	public void setRegiones(Set<Region> regiones) {
		this.regiones = regiones;
	}
	public modeltipoPeriodo getTipoperiodo() {
		return tipoperiodo;
	}
	public void setTipoperiodo(modeltipoPeriodo tipoperiodo) {
		this.tipoperiodo = tipoperiodo;
	}

	
	



}

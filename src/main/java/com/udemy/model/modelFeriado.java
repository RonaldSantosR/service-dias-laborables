package com.udemy.model;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.udemy.entity.Ambito;
import com.udemy.entity.TipoPeriodo;

public class modelFeriado {
	
	private Long id;
	
	private String fecha;
	
	private Set<Ambito> ambitos;
	
	private String nombre;
	
	//private Long periodo;
	
	private modeltipoPeriodo modeltipo;
	/*
	public Long getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Long periodo) {
		this.periodo = periodo;
	}*/
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

		
	
	public Set<Ambito> getAmbitos() {
		return ambitos;
	}
	public void setAmbitos(Set<Ambito> ambitos) {
		this.ambitos = ambitos;
	}
	public modeltipoPeriodo getModeltipo() {
		return modeltipo;
	}
	public void setModeltipo(modeltipoPeriodo modeltipo) {
		this.modeltipo = modeltipo;
	}

	



}

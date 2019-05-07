package com.udemy.model;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.udemy.entity.Ambito;

public class modelFeriado {
	
	private long id;
	
	private String fecha;
	
	private Set<Ambito> Ambito;
	
	private String nombre;
	
	private Long periodo;
	
	public Long getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Long periodo) {
		this.periodo = periodo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public Set<Ambito> getAmbito() {
		return Ambito;
	}
	public void setAmbito(Set<Ambito> ambito) {
		Ambito = ambito;
	}



}

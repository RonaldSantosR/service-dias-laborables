package com.udemy.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.udemy.entity.Ambito;

public class modelFeriado {
	
	private long id;
	
	private String fecha;
	@JsonIgnore
	private Ambito ambito;
	
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
	public Ambito getAmbito() {
		return ambito;
	}
	public void setAmbito(Ambito ambito) {
		this.ambito = ambito;
	}


}

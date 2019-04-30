package com.udemy.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.udemy.entity.Ambito;

public class modelFeriado {
	
	private long id;
	
	private String fecha;
	@JsonIgnore
	private Ambito ambito;
	
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

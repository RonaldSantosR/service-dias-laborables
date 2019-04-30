package com.udemy.model;

import java.util.Date;

import com.udemy.entity.Ambito;

public class modelferiadopermanente extends modelFeriado{
	private long id;
	
	private String fecha;
	
	private String nombre;

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



	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}

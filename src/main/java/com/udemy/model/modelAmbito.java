package com.udemy.model;

import java.util.Set;

import com.udemy.entity.DiaHora;
import com.udemy.entity.Feriado;

public class modelAmbito {
	
	private long id;
	
	private String nombre;
	
	
	private Set<modelDiaHora> diasHora;
	
	
	
	public Set<modelDiaHora> getDiasHora() {
		return diasHora;
	}

	public void setDiasHora(Set<modelDiaHora> diasHora) {
		this.diasHora = diasHora;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
package com.udemy.model;

import java.util.Set;

import com.udemy.entity.DiaHora;
import com.udemy.entity.Feriado;

public class modelRegion {
	
	private long id;
	
	private String nombre;
	
	
	private Set<modelDiaHora> diasLaborables;

	public Set<modelDiaHora> getDiasLaborables() {
		return diasLaborables;
	}

	public void setDiasLaborables(Set<modelDiaHora> diasLaborables) {
		this.diasLaborables = diasLaborables;
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

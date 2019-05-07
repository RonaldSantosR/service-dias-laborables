package com.udemy.model;

import java.util.Set;

import com.udemy.entity.DiaHora;
import com.udemy.entity.Feriado;

public class modelAmbito {
	
	private long id;
	
	private String nombre;
	
	
	private Set<modelDiaHora> diaLaborable;


	public Set<modelDiaHora> getDiaLaborable() {
		return diaLaborable;
	}

	public void setDiaLaborable(Set<modelDiaHora> diaLaborable) {
		this.diaLaborable = diaLaborable;
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

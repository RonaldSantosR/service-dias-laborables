package com.udemy.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.udemy.entity.Region;
import com.udemy.entity.Dia;

public class modelDiaHora {
	
	private long id;
	
	private String inicio;
	
	private String fin;
	
    private modelDia dia;
    
    @JsonIgnore
	private modelRegion region;
	
	private int activo;
	

	public int getActivo() {
		return activo;
	}
	public void setActivo(int activo) {
		this.activo = activo;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getInicio() {
		return inicio;
	}
	public void setInicio(String inicio) {
		this.inicio = inicio;
	}
	public String getFin() {
		return fin;
	}
	public void setFin(String fin) {
		this.fin = fin;
	}
	public modelDia getDia() {
		return dia;
	}
	public void setDia(modelDia dia) {
		this.dia = dia;
	}
	public modelRegion getRegion() {
		return region;
	}
	public void setRegion(modelRegion region) {
		this.region = region;
	}

	
	


}

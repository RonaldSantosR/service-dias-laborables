package com.udemy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="feriado_año")
public class FeriadoAño extends Feriado {
	
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="nombre")
	private String nombre;
	
	private String Asunto;
	
	public String getAsunto() {
		return Asunto;
	}

	public void setAsunto(String asunto) {
		Asunto = asunto;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public FeriadoAño() {

	}	
	
}
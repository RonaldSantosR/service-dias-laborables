package com.udemy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="feriado_permanente")
public class FeriadoPermanente extends Feriado {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="nombre")
	private String nombre;
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}

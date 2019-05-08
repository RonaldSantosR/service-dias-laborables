package com.udemy.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tipo_periodo")
public class TipoPeriodo implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 9066794551606620792L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tipo_periodo_id")
	private Long id;
	
	@Column(name="nombre",nullable=true)
	private String nombre;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	/**
	 * 
	 */
}

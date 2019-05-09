	package com.udemy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="sub_ambito")
public class SubAmbito {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="sub_ambito_id")
	private Long id;
	@Column(nullable=false, unique=true)
	private String nombre;
	@Column(nullable=false)
	private boolean activo;
	
	@ManyToOne(optional=false)	
	@JoinColumn(name="ambito_id")
	private Ambito ambito;
	
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
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public Ambito getAmbito() {
		return ambito;
	}
	public void setAmbito(Ambito ambito) {
		this.ambito = ambito;
	}
	
	
	
}

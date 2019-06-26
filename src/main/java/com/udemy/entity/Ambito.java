	package com.udemy.entity;

import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ambito")
public class Ambito {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ambito_id")
	private Long id;
	@Column(nullable=false, unique=true)
	private String nombre;
	@Column(nullable=false)
	private boolean activo;
	
	@ManyToOne(optional=false)	
	@JoinColumn(name="region_id")
	private Region region;
	
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
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}

	
	
	
}

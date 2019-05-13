package com.udemy.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.udemy.model.modelRegion;

@Entity
@Table(name="region")
public class Region implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="region_id")
	private long id;
	
	private String nombre;

	
	@OneToMany(fetch=FetchType.EAGER , cascade = CascadeType.PERSIST)
	@JoinColumn(name="region_id")
	@JsonIgnore
	private Set<DiaHora> diasLaborables;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="region")
	@JsonIgnore
	private Set<Ambito> subAmbito;

	@ManyToMany(mappedBy = "regiones")
	@JsonIgnore
	private Set<Feriado> feriado;


	public Set<Ambito> getSubAmbito() {
		return subAmbito;
	}

	public void setSubAmbito(Set<Ambito> subAmbito) {
		this.subAmbito = subAmbito;
	}

	public Set<Feriado> getFeriado() {
		return feriado;
	}

	public void setFeriado(Set<Feriado> feriado) {
		this.feriado = feriado;
	}

	public Set<DiaHora> getDiasLaborables() {
			return diasLaborables;
		}
	
	public void setDiasLaborables(Set<DiaHora> diasLaborables) {
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


	private static final long serialVersionUID = 1L;

	
}

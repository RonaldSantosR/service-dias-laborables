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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="ambito")
public class Ambito implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ambito_id")
	private long id;
	
	private String nombre;

	
	@OneToMany(fetch=FetchType.EAGER , cascade = CascadeType.PERSIST)
	@JoinColumn(name="ambito_id")
	private Set<DiaHora> diasHora;
	
	
	
	//Feriados Permanentes
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="ambito_id")
	@JsonIgnore
	private Set<Feriado> feriados;
	
	//
	


	public Set<Feriado> getFeriados() {
		return feriados;
	}


	public void setFeriados(Set<Feriado> feriados) {
		this.feriados = feriados;
	}


	public Set<DiaHora> getDiasHora() {
		return diasHora;
	}


	public void setDiasHora(Set<DiaHora> diasHora) {
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


	private static final long serialVersionUID = 1L;

	
}

package com.udemy.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="feriado")
public class Feriado implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="feriado_id")
	private long id;
	
	@JsonFormat
	(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone="America/Lima") 
	@Temporal(TemporalType.DATE)
	private Date fecha;
	 	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="feriado_ambito", joinColumns = { @JoinColumn(name = "feriado_id") },
    inverseJoinColumns = { @JoinColumn(name = "ambito_id") })
	private Set<Ambito> Ambito;
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private String nombre;
	
	private Long periodo;
	
	
	
	public Long getPeriodo() {
		return periodo;
	}


	public void setPeriodo(Long periodo) {
		this.periodo = periodo;
	}

//
//	public Ambito getAmbito() {
//		return ambito;
//	}
//
//
//
//
//	public void setAmbito(Ambito ambito) {
//		this.ambito = ambito;
//	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	

	public Set<Ambito> getAmbito() {
		return Ambito;
	}


	public void setAmbito(Set<Ambito> ambito) {
		Ambito = ambito;
	}

	private static final long serialVersionUID = 1L;

}

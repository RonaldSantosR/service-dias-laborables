package com.udemy.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;


@Entity
@Table(name="dia_hora")
public class DiaHora implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@JsonFormat
	(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone="America/Lima")
	@Temporal(TemporalType.TIME)
	private Date inicio;

	@JsonFormat
	(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone="America/Lima")
	@Temporal(TemporalType.TIME)
	private Date fin;
	
	//java.sql.Time
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="dia_id",nullable=false)
    private Dia dia;
	
	/*
	@ManyToOne(fetch=FetchType.LAZY)	
	@JoinColumn(name="ambito_id")
	private Ambito ambito;
	*/
	
	/*
	public Ambito getAmbito() {
		return ambito;
	}

	public void setAmbito(Ambito ambito) {
		this.ambito = ambito;
	}*/

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Dia getDia() {
		return dia;
	}

	public void setDia(Dia dia) {
		this.dia = dia;
	}


	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

/*	public void setInicio(java.sql.Time inicio) {
		this.inicio = inicio;
	}


	public void setFin(java.sql.Time fin) {
		this.fin = fin;
	}
*/

	
	private static final long serialVersionUID = 1L;

}

package com.udemy.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="feriado")
@Inheritance(
	    strategy = InheritanceType.JOINED
	)
public class Feriado implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="feriado_id")
	private long id;
	
	
	@JsonFormat
	(shape = JsonFormat.Shape.STRING, pattern = "dd/mm/yyyy", timezone="America/Lima")
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name="ambito_id")
	private Ambito ambito;
	
	
	
	public Ambito getAmbito() {
		return ambito;
	}




	public void setAmbito(Ambito ambito) {
		this.ambito = ambito;
	}




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




	private static final long serialVersionUID = 1L;

}

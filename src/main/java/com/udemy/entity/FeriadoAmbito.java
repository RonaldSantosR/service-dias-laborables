package com.udemy.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class FeriadoAmbito implements Serializable{


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="feriado_ambito_id")
	private long id;
	
	@ManyToOne
    @JoinColumn(name = "feriado_id")
	Feriado feriado;
    @ManyToOne
    @JoinColumn(name = "ambito_id")
    Region ambito;
	
    
    
	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public Feriado getFeriado() {
		return feriado;
	}



	public void setFeriado(Feriado feriado) {
		this.feriado = feriado;
	}



	public Region getAmbito() {
		return ambito;
	}



	public void setAmbito(Region ambito) {
		this.ambito = ambito;
	}



	private static final long serialVersionUID = 1L;

}

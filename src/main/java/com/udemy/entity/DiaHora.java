package com.udemy.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;



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
	
	
	@ManyToOne(fetch=FetchType.LAZY)	
	@JoinColumn(name="region_id")
	@JsonIgnore
	private Region region;
	
	private int activo;

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

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

	@PrePersist
	public void prePersist() {
		this.activo=1;
	}	

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	private static final long serialVersionUID = 1L;

}

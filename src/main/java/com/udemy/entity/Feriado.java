package com.udemy.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name="feriado")
public class Feriado implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="feriado_id")
	private Long id;
	
	@JsonFormat
	(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone="America/Lima") 
	@Temporal(TemporalType.DATE)
	private Date fecha;
	 	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="feriado_region", joinColumns = { @JoinColumn(name = "feriado_id") },
    inverseJoinColumns = { @JoinColumn(name = "region_id") })
	private Set<Region> regiones;
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private String nombre;
	

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipo_periodo_id")
	private TipoPeriodo tipoPeriodo;

	public Long getId() {
		return id;
	}


	public TipoPeriodo getTipoPeriodo() {
		return tipoPeriodo;
	}


	public void setTipoPeriodo(TipoPeriodo tipoPeriodo) {
		this.tipoPeriodo = tipoPeriodo;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}



	public Set<Region> getRegiones() {
		return regiones;
	}


	public void setRegiones(Set<Region> regiones) {
		this.regiones = regiones;
	}

	private static final long serialVersionUID = 1L;

}

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
import com.udemy.model.modelAmbito;

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
	@JsonIgnore
	private Set<DiaHora> diaLaborable;
	
	
	
	//diaLaborable

	/*
	//Feriados Permanentes
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="ambito_id")
	@JsonIgnore
	private Set<Feriado> feriados;
	*/
	//
	
	@ManyToMany(mappedBy = "Ambito")
	@JsonIgnore
	private Set<Feriado> feriado;


	/*
	public Ambito() {
	}
	

	public Ambito(long id, String nombre, Set<DiaHora> diaLaborable ,Set<Feriado> feriados) {
		this.id = id;
		this.nombre = nombre;
		this.diaLaborable = diaLaborable;
		this.feriados = feriados;
	}
*/

/*
	public Ambito(long id, String nombre, Set<DiaHora> diasHora, Set<Feriado> feriados) {
		this.id = id;
		this.nombre = nombre;
		this.diaLaborable = diasHora;
		this.feriados = feriados;
	}
*/





	public Set<DiaHora> getDiaLaborable() {
		return diaLaborable;
	}




	public Set<Feriado> getFeriado() {
		return feriado;
	}




	public void setFeriado(Set<Feriado> feriado) {
		this.feriado = feriado;
	}




	public void setDiaLaborable(Set<DiaHora> diaLaborable) {
		this.diaLaborable = diaLaborable;
	}


/*
	public Set<DiaHora> getDiasHora() {
		return diaLaborable;
	}


	public void setDiasHora(Set<DiaHora> diasHora) {
		this.diaLaborable = diasHora;
	}
*/
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

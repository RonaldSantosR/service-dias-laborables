package com.udemy.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="dia")
public class Dia implements Serializable{
			
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="dia_id")
	private Long id;
	
	private String nombre;	
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy = "dia")
	@JsonIgnore
	private Set<DiaHora> diashora;
	
	
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



	private static final long serialVersionUID = 1L;
}

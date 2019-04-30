package com.udemy.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.udemy.entity.Feriado;


public interface FeriadoRepository extends CrudRepository<Feriado, Long> {
	/*
	@Query(value="SELECT f from Feriado f where f.ambito_id = ?1", nativeQuery=true)
	public Iterable<Feriado> findAllByAmbitoid(Long id);
*/
	@Query("From Feriado f where f.ambito.id=?1 and cast(f.fecha as date) BETWEEN cast(?2 as date) AND cast(?3 as date)")
	public Iterable<Feriado> findAllByAmbitoid(Long id,Date fecha1,Date fecha2);
		
	@Query("From Feriado f where f.ambito.id=?1 and f.fecha<?2")	
	public Iterable<Feriado> buscarferiadospermanentes(Long id,Date fecha);


}

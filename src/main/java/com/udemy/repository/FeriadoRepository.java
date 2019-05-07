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
//	@Query("From Feriado f where f.ambito.id=?1 and cast(f.fecha as date) BETWEEN cast(?2 as date) AND cast(?3 as date)")
//	public Iterable<Feriado> findAllByAmbitoid(Long id,Date fecha1,Date fecha2);
//		
//	
//	@Query("From Feriado f where f.ambito.id=?1 and f.fecha<?2")	
//	public Iterable<Feriado> buscarferiadospermanentes(Long id,Date fecha);

//
//	@Query("From Feriado f where f.id in (select r.id From Feriado r where r.ambito.id=?1) and f.periodo=1")	
//	Iterable<Feriado> findAllByAmbitoid(Long id);
//
//	
//	@Query("From Feriado f where f.id in (select r.id From Feriado r where r.ambito.id=?1)")	
//	Iterable<Feriado> findAllByAmbitoid2(Long id);	
	
	@Query(value=" Select f.fecha From feriado As f where f.feriado_id in (select a.feriado_id From feriado_ambito as a where a.ambito_id=?1) and f.periodo=1", nativeQuery=true)	
	Iterable<Date> findporAmbito(long id);

	@Query(value=" Select f.fecha From feriado As f where f.feriado_id in (select a.feriado_id From feriado_ambito as a where a.ambito_id=?1) and f.periodo=0", nativeQuery=true)	
	Iterable<Date> feriadoañofindporAmbito(long id);	
	
	@Query(value=" Select * From feriado As f where f.feriado_id in (select a.feriado_id From feriado_ambito as a where a.ambito_id=?1)", nativeQuery=true)	
	Iterable<Feriado> findAllByAmbitoid2(long id);		

	@Query(value=" Select * From feriado As f where f.feriado_id in (select a.feriado_id From feriado_ambito as a where a.ambito_id=?1) and cast(f.fecha as date) BETWEEN cast(?2 as date) AND cast(?3 as date)", nativeQuery=true)	
	Iterable<Feriado> findAllByAmbitoidentrefechas(long id ,Date fecha1,Date fecha2);		
	
	@Query(value=" Select * From feriado As f where f.feriado_id in (select a.feriado_id From feriado_ambito as a where a.ambito_id=?1)", nativeQuery=true)	
	public Iterable<Feriado> findAllByAmbitoid(Long id);

	@Query(value=" Select * From feriado As f where f.feriado_id in (select a.feriado_id From feriado_ambito as a where a.ambito_id=?1) and f.periodo=0 and cast(f.fecha as date) BETWEEN cast(?2 as date) AND cast(?3 as date)", nativeQuery=true)	
	Iterable<Feriado> findAllByAmbitoidentrefechasaño(long id ,Date fecha1,Date fecha2);	
}

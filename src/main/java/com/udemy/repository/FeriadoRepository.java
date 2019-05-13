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
	
	@Query(value=" Select f.fecha From feriado As f where f.feriado_id in (select a.feriado_id From feriado_region as a where a.region_id=?1) and f.tipo_periodo_id in (select tf.tipo_periodo_id from tipo_periodo as tf where tf.tipo_periodo_id=1) ", nativeQuery=true)	
	Iterable<Date> findporAmbito(long id);

	@Query(value=" Select f.fecha From feriado As f where f.feriado_id in (select a.feriado_id From feriado_region as a where a.region_id=?1) and f.tipo_periodo_id in (select tf.tipo_periodo_id from tipo_periodo as tf where tf.tipo_periodo_id=2)", nativeQuery=true)	
	Iterable<Date> feriadoañofindporAmbito(long id);	
	
	@Query(value=" Select * From feriado As f where f.feriado_id in (select a.feriado_id From feriado_region as a where a.region_id=?1)", nativeQuery=true)	
	Iterable<Feriado> findAllByAmbitoid2(long id);		

	@Query(value=" Select * From feriado As f where f.feriado_id in (select a.feriado_id From feriado_region as a where a.region_id=?1) and cast(f.fecha as date) BETWEEN cast(?2 as date) AND cast(?3 as date)", nativeQuery=true)	
	Iterable<Feriado> findAllByAmbitoidentrefechas(long id ,Date fecha1,Date fecha2);		
	
	@Query(value=" Select * From feriado As f where f.feriado_id in (select a.feriado_id From feriado_region as a where a.region_id=?1)", nativeQuery=true)	
	public Iterable<Feriado> findAllByAmbitoid(Long id);


	
	@Query(value=" Select * From feriado As f where f.feriado_id in (select a.feriado_id From feriado_region as a where a.region_id=?2) and cast(f.fecha as date)=?1 ", nativeQuery=true)	
	public Feriado esferiado(Date fecha, Long ambitoId);

	
//	@Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Feriado f WHERE cast(f.fecha as date)=?1 ")
//	boolean esferiado(Date fecha);

	@Query(value=" Select * From feriado As f where f.feriado_id in (select a.feriado_id From feriado_region as a where a.region_id=?1) and f.tipo_periodo_id in (select tf.tipo_periodo_id from tipo_periodo as tf where tf.tipo_periodo_id=2) and cast(f.fecha as date) BETWEEN cast(?2 as date) AND cast(?3 as date)", nativeQuery=true)	
	Iterable<Feriado> findAllByAmbitoidentrefechasaño(long id ,Date fecha1,Date fecha2);	
	
	@Query("From Feriado f where f.nombre=?1")
	Feriado findbyNombre(String nombre);	

}

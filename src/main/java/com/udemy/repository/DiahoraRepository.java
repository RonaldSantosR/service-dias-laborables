package com.udemy.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.udemy.entity.DiaHora;

import com.udemy.entity.Region;

@Repository
public interface DiahoraRepository extends CrudRepository<DiaHora, Long> {
	
	
	@Query("FROM DiaHora d WHERE d.region.id= ?1 ")
	Iterable<DiaHora> findhorasById(Long id);

	@Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM DiaHora d WHERE d.dia.id=?1 AND d.region.id=?2  AND d.activo=1")
	boolean esdialaborable(Long diaId, Long ambitoId);
	
	
	@Query("SELECT fin FROM DiaHora d WHERE d.dia.id=?1 AND d.region.id=?2")
	Date horasalida(Long diaId, Long ambitoId);	

}

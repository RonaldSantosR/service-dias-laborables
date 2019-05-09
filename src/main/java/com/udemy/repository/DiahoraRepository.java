package com.udemy.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.udemy.entity.DiaHora;

import com.udemy.entity.Ambito;

@Repository
public interface DiahoraRepository extends CrudRepository<DiaHora, Long> {
	
	
	@Query("FROM DiaHora d WHERE d.ambito.id= ?1 ")
	Iterable<DiaHora> findhorasById(Long id);

	@Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM DiaHora d WHERE d.dia.id=?1 AND d.ambito.id=?2  AND d.activo=1")
	boolean esdialaborable(Long diaId, Long ambitoId);
	
}

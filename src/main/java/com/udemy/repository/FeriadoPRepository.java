package com.udemy.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.udemy.entity.Ambito;
import com.udemy.entity.FeriadoAño;
import com.udemy.entity.FeriadoPermanente;

public interface FeriadoPRepository extends CrudRepository<FeriadoPermanente, Long> {
	
	
	//@Query(value="SELECT plazo_distribucion_id from area_plazo_distribucion where area_id = ?1", nativeQuery=true)
	@Query("From FeriadoPermanente f where f.id in (select r.id From Feriado r where r.ambito.id=?1)")	
	Iterable<FeriadoPermanente> findAllByAmbitoid(Long id);

}

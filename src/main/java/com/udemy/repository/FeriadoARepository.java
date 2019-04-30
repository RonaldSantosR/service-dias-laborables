package com.udemy.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.udemy.entity.FeriadoAño;

public interface FeriadoARepository extends CrudRepository<FeriadoAño, Long> {

	@Query("From FeriadoAño f where f.id in (select r.id From Feriado r where r.ambito.id=?1 and  cast(r.fecha as date) BETWEEN cast(?2 as date) AND cast(?3 as date))")	
	Iterable<FeriadoAño> findAllByAmbitoid(Long id,Date fecha1,Date fecha2);

}


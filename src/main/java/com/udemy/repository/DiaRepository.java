package com.udemy.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.udemy.entity.Dia;

public interface DiaRepository extends CrudRepository<Dia, Long> {
	
	List<Dia> findAll();
	
	@Query("FROM Dia d WHERE d IN (select p.dia from DiaHora p where p.ambito.id=?1)")
	public Iterable<Dia> findByDiaPorAmbito(Long id);	
	


}

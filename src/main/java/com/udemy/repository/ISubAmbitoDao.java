package com.udemy.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.udemy.entity.SubAmbito;


@Repository
public interface ISubAmbitoDao extends CrudRepository<SubAmbito, Long>{

	@Query("FROM SubAmbito sa WHERE sa.ambito.id=?1")
	public Iterable<SubAmbito> listarSubAmbitoByAmbitoId(Long id);

	@Query("FROM SubAmbito sa WHERE sa.nombre=?1")	
	public SubAmbito findByNombre(String nombre);
	
}

package com.udemy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.udemy.entity.Ambito;


@Repository
public interface IAmbitoDao extends CrudRepository<Ambito, Long>{

	@Query("FROM Ambito sa WHERE sa.region.id=?1")
	public Iterable<Ambito> listarSubAmbitoByAmbitoId(Long id);

	@Query("FROM Ambito sa WHERE sa.nombre=?1")	
	public Ambito findByNombre(String nombre);
	
	
	@Query("FROM Ambito a WHERE a.region.id=?1")	
	public Iterable<Ambito> findAmbitosByRegionId(Long regionId);
	
	public Iterable<Ambito> findAllByNombreIn(List<String> nombre);
}

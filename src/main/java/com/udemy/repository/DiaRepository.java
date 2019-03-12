package com.udemy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.udemy.entity.Dia;

public interface DiaRepository extends CrudRepository<Dia, Long> {
	
	List<Dia> findAll();
	
}

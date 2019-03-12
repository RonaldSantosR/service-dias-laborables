package com.udemy.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.udemy.component.examplecomponent;
import com.udemy.entity.Ambito;


@Repository
public interface AmbitoRepository extends CrudRepository<Ambito, Long>{

	
	
	
}
	
package com.udemy.repository;

import org.springframework.data.repository.CrudRepository;

import com.udemy.entity.Ambito;
import com.udemy.entity.FeriadoPermanente;

public interface FeriadoPRepository extends CrudRepository<FeriadoPermanente, Long> {

}

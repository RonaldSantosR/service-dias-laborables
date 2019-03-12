package com.udemy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.udemy.entity.DiaHora;

import com.udemy.entity.Ambito;

@Repository
public interface DiahoraRepository extends CrudRepository<DiaHora, Long> {

}

package com.udemy.repository;
import com.udemy.entity.*;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("coursejparepository")
public interface coursejparepository extends JpaRepository<course, Serializable>{

	public abstract List<course> findAll();
	
	public abstract course save(course course);
		
	public abstract course deleteById(int id);

}

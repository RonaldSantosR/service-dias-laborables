package com.udemy.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.udemy.entity.Region;


@Repository
public interface RegionRepository extends CrudRepository<Region, Long>{

	@Query("FROM Region r WHERE r IN (SELECT a.region FROM Ambito a WHERE a.id=?1)")
	public Region findRegionByAmbitoId(Long ambitoId);
	
	
}
	
package com.example.irm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.irm.model.Relationship;
//import com.example.irm.utils.RelationType;

public interface RelationshipRepository extends CrudRepository<Relationship, Long>
{
	@Query(value="SELECT r FROM relationship WHERE r.EntitySource = :entitySrc",nativeQuery = true)
	List<Relationship> findByEntitySrc(String entitySrc);
	
	@Query(value="SELECT r FROM relationship WHERE r.EntityTarget = :entityTgt",nativeQuery = true)
	List<Relationship> findByEntityTgt(String entityTgt);
	
	//@Query("SELECT r FROM relationship WHERE r.RelationTypeSource2Target = :relationType")
	//List<Relationship> findByRelationshipS2T(RelationType relationType);
	
	//@Query("SELECT r FROM relationship WHERE r.RelationTypeTarget2Source = :relationType")
	//List<Relationship> findByRelationshipT2S(RelationType relationType);
	
	List<Relationship> findAll();
}
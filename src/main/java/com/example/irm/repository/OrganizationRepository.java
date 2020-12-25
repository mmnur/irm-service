package com.example.irm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.irm.model.Organization;

public interface OrganizationRepository extends CrudRepository<Organization, Long>
{
	List<Organization> findByUsername(String username);
	List<Organization> findByEmail(String email);
	List<Organization> findByEntityId(String entityID);
	List<Organization> findAll();
}
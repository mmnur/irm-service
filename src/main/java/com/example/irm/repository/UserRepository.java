package com.example.irm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.irm.model.User;

public interface UserRepository extends CrudRepository<User, Long>
{
	List<User> findByUsername(String username);
	List<User> findByEmail(String email);
	List<User> findByEntityId(String entityID);
	List<User> findAll();
}
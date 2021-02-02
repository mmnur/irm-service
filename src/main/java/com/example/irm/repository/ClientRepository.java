package com.example.irm.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.irm.model.Client;

public interface ClientRepository extends CrudRepository<Client, Long>
{
	List<Client> findByClientId(String clientId);
	List<Client> findAll();
}
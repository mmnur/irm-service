package com.example.irm.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.irm.model.IamService;

public interface IamServiceRepository extends CrudRepository<IamService, Long>
{
	List<IamService> findByClientId(String clientId);
	List<IamService> findAll();
}
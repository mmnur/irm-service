package com.example.irm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.irm.model.Device;

public interface DeviceRepository extends CrudRepository<Device, Long>
{
	List<Device> findByEntityId(String entityID);
	List<Device> findByOwnerEID(String ownerEID);
	List<Device> findByChallenge(String challenge);
	List<Device> findByPublicKey(String publicKey);
	List<Device> findAll();
}
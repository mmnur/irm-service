package com.example.irm.controller;

import com.example.irm.error.IrmCryptographyException;
import com.example.irm.error.IrmNotFoundException;
import com.example.irm.model.Device;
import com.example.irm.model.Organization;
import com.example.irm.model.User;
import com.example.irm.repository.DeviceRepository;
import com.example.irm.repository.OrganizationRepository;
import com.example.irm.repository.UserRepository;
import com.example.irm.utils.PkiUtil;
import com.example.irm.view.DeviceUI;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
 
 
@RestController
public class DeviceController
{
	@Autowired
	DeviceRepository deviceRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrganizationRepository orgRepository;
	
	@PostMapping(path = "/devices/initreg", consumes = "application/json")
	public String init_register(@RequestBody DeviceUI device)
			throws IrmNotFoundException
	{
		deviceRepository.save(new Device(device.getDisplayName(), device.getOwnerEID(), device.getPublicKey(), device.getScopes()));
		List<User> users = userRepository.findByEntityId(device.getOwnerEID());
		if (users.size() < 1) {
			List<Organization> orgs = orgRepository.findByEntityId(device.getOwnerEID());
			if (orgs.size() < 1) {
				throw new IrmNotFoundException("Owner not found: " + device.getOwnerEID());
			}
		}
		
		return deviceRepository.findByPublicKey(device.getPublicKey()).get(0).getChallenge();		
	}
	
	@PostMapping(path = "/devices/finishreg", consumes = "application/json")
	public String finish_register(@RequestBody DeviceUI device)
			throws Exception
	{
		String challenge = device.getChallenge();
		String challengeResponse = device.getChallengeResponse();

		List<Device> devicesMatchingChallenge = deviceRepository.findByChallenge(challenge);		
		if (devicesMatchingChallenge.size() < 1 || devicesMatchingChallenge.size() > 1) {
			throw new IrmNotFoundException("Challenge not found or more than one matching challenge found: " + challenge);
		}

		String publicKey = devicesMatchingChallenge.get(0).getPublicKey();
		PublicKey pubKey = PkiUtil.rebuildPublicKey(publicKey);
		if (!PkiUtil.verify(challenge, challengeResponse, pubKey)) {
			throw new IrmCryptographyException(String.format("Unable to verify challenge resposne: challange=%s, response=%s ", challenge, challengeResponse));
		}

		Device deviceToUpdate = devicesMatchingChallenge.get(0);
		deviceToUpdate.setChallenge(null);
		deviceToUpdate.setRegisterationCompleted(true);
		deviceRepository.save(deviceToUpdate);	

		return device.getEntityId();		
	}
	
	/*
	@GetMapping(path = "/devices/findall")
	public List<DeviceUI> findAll()
	{
		List<Device> devices = repository.findAll();
		List<DeviceUI> deviceUI = new ArrayList<>();
		
		for (Device device : devices) {
			deviceUI.add(new DeviceUI(device.getEntityId(), device.getDisplayName(), device.getEmail(), device.getDevicename()));
		}

		return deviceUI;
	}
	
	@RequestMapping(path = "/devices/eid/{entityId}")
	public String searchByEntityId(@PathVariable String entityId){
		String device = "";
		device = repository.findByEntityId(entityId).toString();
		return device;
	}
	
	@RequestMapping(path = "/devices/email/{email}")
	public String searchByEmail(@PathVariable String email){
		String device = "";
		device = repository.findByEmail(email).toString();
		return device;
	}

	@RequestMapping(path = "/devices/devicename/{devicename}")
	public List<DeviceUI> fetchDataByDevicename(@PathVariable String devicename){
	
		List<Device> devices = repository.findByName(devicename);
		List<DeviceUI> deviceUI = new ArrayList<>();
		
		for (Device device : devices) {
			deviceUI.add(new DeviceUI(device.getDisplayName(), device.getEmail(), device.getName(), device.getPassword()));
		}

		return deviceUI;
	}
	*/
}
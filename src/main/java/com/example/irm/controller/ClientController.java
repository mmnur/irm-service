package com.example.irm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.irm.error.IrmNotFoundException;
import com.example.irm.model.Client;
import com.example.irm.repository.ClientRepository;
import com.example.irm.view.ClientUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
 
@RestController
@RequestMapping("/clients")
public class ClientController
{
	@Autowired
	ClientRepository clientRepository;
	
	@PostMapping(
			path = "/register",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> create(@RequestBody ClientUI clientUI)
	{
		Client client = new Client(clientUI.getDisplayName(), clientUI.getClientId(), clientUI.getClientSecret());
		Client is = clientRepository.save(client);
		
		Map<String, String> retValue = new HashMap<String, String>();		
		retValue.put("RefNo", is.getId());
		
		return retValue;
	}
	
	@PostMapping(
			path = "/authenticate", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> authenticate(@RequestBody ClientUI clientUI)
			throws IrmNotFoundException
	{
		List<Client> iamServices = clientRepository.findByClientId(clientUI.getClientId());		
		if (iamServices.isEmpty()) {
			throw new IrmNotFoundException("Client ID or secret not found!");
		}
		
		Client iamSvc = iamServices.get(0);
		String clientSecret = iamSvc.getClientSecret();
		if (clientUI.getClientSecret().equals(clientSecret)) {
			Map<String, String> retValue = new HashMap<String, String>();		
			retValue.put("RefId", iamSvc.getId());			
			return retValue;
		}
		
		throw new IrmNotFoundException("Username or password not found!");
	}
}
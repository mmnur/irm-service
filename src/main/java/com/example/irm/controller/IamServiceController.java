package com.example.irm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.irm.error.IrmNotFoundException;
import com.example.irm.model.IamService;
import com.example.irm.repository.IamServiceRepository;
import com.example.irm.view.IamServiceUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
 
@RestController
@RequestMapping("/clients")
public class IamServiceController
{
	@Autowired
	IamServiceRepository iamServiceRepository;
	
	@PostMapping(
			path = "/register",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> create(@RequestBody IamServiceUI iamServiceUI)
	{
		IamService iamService = new IamService(iamServiceUI.getDisplayName(), iamServiceUI.getClientId(), iamServiceUI.getClientSecret());
		IamService is = iamServiceRepository.save(iamService);
		
		Map<String, String> retValue = new HashMap<String, String>();		
		retValue.put("RefNo", is.getId());
		
		return retValue;
	}
	
	@PostMapping(
			path = "/authenticate", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> authenticate(@RequestBody IamServiceUI iamServiceUI)
			throws IrmNotFoundException
	{
		List<IamService> iamServices = iamServiceRepository.findByClientId(iamServiceUI.getClientId());		
		if (iamServices.isEmpty()) {
			throw new IrmNotFoundException("Client ID or secret not found!");
		}
		
		IamService iamSvc = iamServices.get(0);
		String clientSecret = iamSvc.getClientSecret();
		if (iamServiceUI.getClientSecret().equals(clientSecret)) {
			Map<String, String> retValue = new HashMap<String, String>();		
			retValue.put("RefId", iamSvc.getId());			
			return retValue;
		}
		
		throw new IrmNotFoundException("Username or password not found!");
	}
}
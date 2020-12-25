package com.example.irm.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.irm.model.Organization;
import com.example.irm.repository.OrganizationRepository;
import com.example.irm.view.OrganizationUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
 
@RestController
public class OrganizationController
{
	@Autowired
	OrganizationRepository repository;
	
	@GetMapping(path = "/orgs/bulkregister")
	public String bulkcreate()
	{
		// save a list of Organizations
        repository.saveAll(Arrays.asList(
        		new Organization("Premier Hospital", "admin@premier.com", "adminpm", "adminpm123")
        		, new Organization("Tech Zilla", "admin@techzilla.com", "admintz", "admintz123")));
        
		return "Demo organizations are created";
	}
	
	@PostMapping(path = "/orgs/register", consumes = "application/json")
	public String create(@RequestBody OrganizationUI org)
	{
		// save a single Organization
		repository.save(new Organization(org.getDisplayName(), org.getEmail(), org.getUsername(), org.getPassword()));		

		return "EID = " + repository.findByEmail(org.getEmail()).get(0).getEntityId();
	}
	
	@GetMapping(path = "/orgs/findall")
	public List<OrganizationUI> findAll()
	{
		List<Organization> orgs = repository.findAll();
		List<OrganizationUI> orgUI = new ArrayList<>();
		
		for (Organization org : orgs) {
			orgUI.add(new OrganizationUI(org.getEntityId(), org.getDisplayName(), org.getEmail(), org.getUsername()));
		}

		return orgUI;
	}
	
	@RequestMapping(path = "/orgs/eid/{entityId}")
	public String searchByEntityId(@PathVariable String entityId){
		String org = "";
		org = repository.findByEntityId(entityId).toString();
		return org;
	}
	
	@RequestMapping(path = "/orgs/email/{email}")
	public String searchByEmail(@PathVariable String email){
		String org = "";
		org = repository.findByEmail(email).toString();
		return org;
	}

	@RequestMapping(path = "/orgs/orgname/{username}")
	public List<OrganizationUI> fetchDataByUsername(@PathVariable String username){
	
		List<Organization> orgs = repository.findByUsername(username);
		List<OrganizationUI> orgUI = new ArrayList<>();
		
		for (Organization org : orgs) {
			orgUI.add(new OrganizationUI(org.getDisplayName(), org.getEmail(), org.getUsername(), org.getPassword()));
		}

		return orgUI;
	}
}
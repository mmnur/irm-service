package com.example.irm.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.irm.error.AlreadyExistsException;
import com.example.irm.error.IrmOrgNotFoundException;
import com.example.irm.graph.Entity;
import com.example.irm.graph.RelationshipGraph;
import com.example.irm.model.Organization;
import com.example.irm.repository.OrganizationRepository;
import com.example.irm.utils.EntityType;
import com.example.irm.view.OrganizationUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
@RequestMapping("/orgs")
public class OrganizationController
{
	@Autowired
	OrganizationRepository repository;
	
	@GetMapping(path = "/bulkregister")
	public Iterable<Organization> bulkcreate()
	{
		// save a list of Organizations
		Iterable<Organization> orgs = repository.saveAll(Arrays.asList(
				new Organization("Premier Hospital", "admin@premier.com", "adminpm", "adminpm123")
        		, new Organization("Tech Zilla", "admin@techzilla.com", "admintz", "admintz123")));

		for (Organization org: orgs) {
			String eid = org.getEntityId();		
			Entity entity = new Entity(eid, EntityType.Device); 
			RelationshipGraph.getGraph().addEntity(entity);
		}

		return orgs;
	}
	
	@PostMapping(
			path = "/register",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> create(@RequestBody OrganizationUI orgUI)
			throws AlreadyExistsException
	{
		List<Organization> orgs = repository.findByUsername(orgUI.getUsername());		
		if (!orgs.isEmpty()) {
			throw new AlreadyExistsException("An organization already exists with the same username");
		}
		
		orgs = repository.findByEmail(orgUI.getEmail());
		if (!orgs.isEmpty()) {
			throw new AlreadyExistsException("An organization already exists with the same email");
		}
		
		repository.save(new Organization(orgUI.getDisplayName(), orgUI.getEmail(), orgUI.getUsername(), orgUI.getPassword()));
		
		String eid = repository.findByEmail(orgUI.getEmail()).get(0).getEntityId();		
		Entity entity = new Entity(eid, EntityType.Device); 
		RelationshipGraph.getGraph().addEntity(entity);
		
		Map<String, String> retValue = new HashMap<String, String>();		
		retValue.put("entityId", eid);
		
		return retValue;
	}
	
	@GetMapping(path = "/findall")
	public List<OrganizationUI> findAll()
	{
		List<Organization> orgs = repository.findAll();
		List<OrganizationUI> orgUI = new ArrayList<>();
		
		for (Organization org : orgs) {
			orgUI.add(new OrganizationUI(org.getEntityId(), org.getDisplayName(), org.getEmail(), org.getUsername()));
		}

		return orgUI;
	}

	@PostMapping(
			path = "/authenticate", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> authenticate(@RequestBody OrganizationUI orgUI)
			throws IrmOrgNotFoundException
	{
		List<Organization> orgs = repository.findByUsername(orgUI.getUsername());		
		if (orgs.isEmpty()) {
			throw new IrmOrgNotFoundException("Username or password not found!");
		}
		
		Organization org = orgs.get(0);
		String password = org.getPassword();
		if (orgUI.getPassword().equals(password)) {
			Map<String, String> retValue = new HashMap<String, String>();		
			retValue.put("entityId", org.getEntityId());			
			return retValue;			
		}
		throw new IrmOrgNotFoundException("Username or password not found!");
	}
	
	@RequestMapping(path = "/eid/{entityId}")
	public String searchByEntityId(@PathVariable String entityId){
		String org = "";
		org = repository.findByEntityId(entityId).toString();
		return org;
	}
	
	@RequestMapping(path = "/email/{email}")
	public String searchByEmail(@PathVariable String email){
		String org = "";
		org = repository.findByEmail(email).toString();
		return org;
	}

	@RequestMapping(path = "/orgname/{username}")
	public List<OrganizationUI> fetchDataByUsername(@PathVariable String username){
	
		List<Organization> orgs = repository.findByUsername(username);
		List<OrganizationUI> orgUI = new ArrayList<>();
		
		for (Organization org : orgs) {
			orgUI.add(new OrganizationUI(org.getDisplayName(), org.getEmail(), org.getUsername(), org.getPassword()));
		}

		return orgUI;
	}
}
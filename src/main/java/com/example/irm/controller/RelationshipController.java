package com.example.irm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.irm.error.IrmNotFoundException;
import com.example.irm.graph.Entity;
import com.example.irm.graph.RelationshipGraph;
import com.example.irm.model.Organization;
import com.example.irm.model.Relationship;
import com.example.irm.model.User;
import com.example.irm.repository.OrganizationRepository;
import com.example.irm.repository.RelationshipRepository;
import com.example.irm.repository.UserRepository;
import com.example.irm.view.RelationshipUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
 
@RestController
@RequestMapping("/relationships")
public class RelationshipController
{
	@Autowired
	RelationshipRepository relationshipRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrganizationRepository organizationRepository;	
	
	@PostMapping(
			path = "/register",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> create(@RequestBody RelationshipUI relationshipUI) throws IrmNotFoundException
	{
		Entity entitySrc = RelationshipGraph.getGraph().findEntity(relationshipUI.getEntitySrc());		
		if (entitySrc == null) {
			throw new IrmNotFoundException("Not found: " + relationshipUI.getEntitySrc());
		}
		
		Entity entityTgt = RelationshipGraph.getGraph().findEntity(relationshipUI.getEntityTgt());
		if (entityTgt == null) {
			throw new IrmNotFoundException("Not found: " + relationshipUI.getEntityTgt());
		}

		entitySrc.addRelation(entityTgt, relationshipUI.getRelationTypeS2T());
		entityTgt.addRelation(entitySrc, relationshipUI.getRelationTypeT2S());

		Relationship relationship = new Relationship(relationshipUI.getEntitySrc(), relationshipUI.getEntityTgt(), relationshipUI.getRelationTypeS2T(), relationshipUI.getRelationTypeT2S());
		Relationship r = relationshipRepository.save(relationship);
		
		Map<String, String> retValue = new HashMap<String, String>();		
		retValue.put("RefNo", r.getId());
		
		return retValue;
	}
	
	@PostMapping(
			path = "/registerbyemail",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> createbyemail(@RequestBody RelationshipUI relationshipUI)
			throws IrmNotFoundException
	{
		List<User> users = userRepository.findByEmail(relationshipUI.getEntitySrc());
		String entitySrcId = null;
		if (users.size() > 0) {
			entitySrcId = users.get(0).getEntityId();			
		} else {
			List<Organization> orgs = organizationRepository.findByEmail(relationshipUI.getEntitySrc());
			if (orgs.size() > 0) {
				entitySrcId = orgs.get(0).getEntityId();
			} else {
				throw new IrmNotFoundException("Not found: " + relationshipUI.getEntitySrc());
			}
		}
		
		users = userRepository.findByEmail(relationshipUI.getEntityTgt());
		String entityTgtId = null;
		if (users.size() > 0) {
			entityTgtId = users.get(0).getEntityId();			
		} else {
			List<Organization> orgs = organizationRepository.findByEmail(relationshipUI.getEntityTgt());
			if (orgs.size() > 0) {
				entityTgtId = orgs.get(0).getEntityId();
			} else {
				throw new IrmNotFoundException("Not found: " + relationshipUI.getEntityTgt());
			}
		}
		
		Relationship relationship = new Relationship(
				entitySrcId, entityTgtId, relationshipUI.getRelationTypeS2T(), relationshipUI.getRelationTypeT2S());
		Relationship r = relationshipRepository.save(relationship);		

		Entity entitySrc = RelationshipGraph.getGraph().findEntity(relationship.getEntitySrc());
		Entity entityTgt = RelationshipGraph.getGraph().findEntity(relationship.getEntityTgt());
		entitySrc.addRelation(entityTgt, relationshipUI.getRelationTypeS2T());
		entityTgt.addRelation(entitySrc, relationshipUI.getRelationTypeT2S());

		Map<String, String> retValue = new HashMap<String, String>();		
		retValue.put("RefNo", r.getId());
		
		return retValue;
	}	
	
	@GetMapping(
			path = "/find",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<List<RelationshipUI>> findRelationship(
			@RequestParam String entityIdSrc,
			@RequestParam String entityIdTgt,
			@RequestParam int maxDegreeOfRelationship)
					throws IrmNotFoundException
	{
		Entity entitySrc = RelationshipGraph.getGraph().findEntity(entityIdSrc);
		if (entitySrc == null) {
			throw new IrmNotFoundException("Not found: " + entityIdSrc);
		}

		Entity entityTgt = RelationshipGraph.getGraph().findEntity(entityIdTgt);
		if (entityTgt == null) {
			throw new IrmNotFoundException("Not found: " + entityIdTgt);
		}
		
		List<List<Relationship>> allRelationships = RelationshipGraph.getGraph().findRelationships(entityIdSrc, entityIdTgt, maxDegreeOfRelationship);
		
		if (allRelationships.size() > 0) {
			List<List<RelationshipUI>> allRelationshipUIs = new ArrayList<List<RelationshipUI>>();			
			for (List<Relationship> relationships : allRelationships) {
				List<RelationshipUI> relationshipsUI = new ArrayList<RelationshipUI>();
				for (Relationship relationship : relationships) {
					relationshipsUI.add(new RelationshipUI(relationship.getEntitySrc(), relationship.getEntityTgt(), relationship.getRelationTypeS2T().toString(), relationship.getRelationTypeT2S().toString()));
				}
				allRelationshipUIs.add(relationshipsUI);
			}		
			
			return allRelationshipUIs;
		}

		throw new IrmNotFoundException("No relationship found within degree of relationship " + maxDegreeOfRelationship);
	}
	
	@GetMapping(
			path = "/findbyemail",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<List<RelationshipUI>> findRelationshipByEmail(
			@RequestParam String entitySrcEmail,
			@RequestParam String entityTgtEmail,
			@RequestParam int maxDegreeOfRelationship)
					throws IrmNotFoundException
	{
		List<User> users = userRepository.findByEmail(entitySrcEmail);
		String entitySrcId = null;
		if (users.size() > 0) {
			entitySrcId = users.get(0).getEntityId();			
		} else {
			List<Organization> orgs = organizationRepository.findByEmail(entitySrcEmail);
			if (orgs.size() > 0) {
				entitySrcId = orgs.get(0).getEntityId();
			} else {
				throw new IrmNotFoundException("Not found: " + entitySrcEmail);
			}
		}
		
		users = userRepository.findByEmail(entityTgtEmail);
		String entityTgtId = null;
		if (users.size() > 0) {
			entityTgtId = users.get(0).getEntityId();			
		} else {
			List<Organization> orgs = organizationRepository.findByEmail(entityTgtEmail);
			if (orgs.size() > 0) {
				entityTgtId = orgs.get(0).getEntityId();
			} else {
				throw new IrmNotFoundException("Not found: " + entityTgtEmail);
			}
		}
		
		List<List<Relationship>> allRelationships = RelationshipGraph.getGraph().findRelationships(
				entitySrcId, entityTgtId, maxDegreeOfRelationship);
				
		if (allRelationships.size() > 0) {
			List<List<RelationshipUI>> allRelationshipUIs = new ArrayList<List<RelationshipUI>>();			
			for (List<Relationship> relationships : allRelationships) {
				List<RelationshipUI> relationshipsUI = new ArrayList<RelationshipUI>();
				for (Relationship relationship : relationships) {
					relationshipsUI.add(new RelationshipUI(relationship.getEntitySrc(), relationship.getEntityTgt(), relationship.getRelationTypeS2T().toString(), relationship.getRelationTypeT2S().toString()));
				}
				allRelationshipUIs.add(relationshipsUI);
			}			
			
			return allRelationshipUIs;
		}
		
		throw new IrmNotFoundException("No relationship found within degree of relationship " + maxDegreeOfRelationship);
	}
	
	@GetMapping(
			path = "/findbytype",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<List<RelationshipUI>> findRelationshipByOrgType(
			@RequestParam String actorEntityIdSrc,
			@RequestParam String orgType,
			@RequestParam int maxDegreeOfRelationship)
					throws IrmNotFoundException
	{
		Entity entitySrc = RelationshipGraph.getGraph().findEntity(actorEntityIdSrc);
		if (entitySrc == null) {
			throw new IrmNotFoundException("Not found: " + actorEntityIdSrc);
		}

		List<Organization> orgs = organizationRepository.findByType(orgType);
		for(Organization org: orgs) {
			List<List<Relationship>> allRelationships = RelationshipGraph.getGraph().findRelationships(actorEntityIdSrc, org.getEntityId(), maxDegreeOfRelationship);
			if (allRelationships.size() > 0) {
				List<List<RelationshipUI>> allRelationshipUIs = new ArrayList<List<RelationshipUI>>();			
				for (List<Relationship> relationships : allRelationships) {
					List<RelationshipUI> relationshipsUI = new ArrayList<RelationshipUI>();
					for (Relationship relationship : relationships) {
						relationshipsUI.add(new RelationshipUI(relationship.getEntitySrc(), relationship.getEntityTgt(), relationship.getRelationTypeS2T().toString(), relationship.getRelationTypeT2S().toString()));
					}
					allRelationshipUIs.add(relationshipsUI);
				}		
				
				return allRelationshipUIs;
			}
		}

		throw new IrmNotFoundException("No relationship found within degree of relationship " + maxDegreeOfRelationship);
	}
	
	@GetMapping(
			path = "/findall",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RelationshipUI> findAll()
	{
		List<Relationship> relationships = relationshipRepository.findAll();
		List<RelationshipUI> relationshipsUI = new ArrayList<>();
		
		for (Relationship relationship : relationships) {
			relationshipsUI.add(new RelationshipUI(relationship.getEntitySrc(), relationship.getEntityTgt(), relationship.getRelationTypeS2T().toString(), relationship.getRelationTypeT2S().toString()));
		}

		return relationshipsUI;
	}
}
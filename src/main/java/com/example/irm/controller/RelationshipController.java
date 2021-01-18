package com.example.irm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.irm.error.IrmNotFoundException;
import com.example.irm.graph.Entity;
import com.example.irm.graph.RelationshipGraph;
import com.example.irm.model.Relationship;
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
	
	@PostMapping(
			path = "/register",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> create(@RequestBody RelationshipUI relationshipUI)
	{
		Relationship relationship = new Relationship(relationshipUI.getEntitySrc(), relationshipUI.getEntityTgt(), relationshipUI.getRelationTypeS2T(), relationshipUI.getRelationTypeT2S());
		Relationship r = relationshipRepository.save(relationship);
		
		Entity entitySrc = RelationshipGraph.getGraph().findEntity(relationship.getEntitySrc());
		Entity entityTgt = RelationshipGraph.getGraph().findEntity(relationship.getEntityTgt());

		entitySrc.addRelation(entityTgt, relationship.getRelationTypeS2T());
		entityTgt.addRelation(entitySrc, relationship.getRelationTypeT2S());

		Map<String, String> retValue = new HashMap<String, String>();		
		retValue.put("RefNo", r.getId());
		
		return retValue;
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
	
	@PostMapping(
			path = "/registerbyemail",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> createbyemail(@RequestBody RelationshipUI relationshipUI)
	{
		Relationship relationship = new Relationship(
				userRepository.findByEmail(relationshipUI.getEntitySrc()).get(0).getEntityId(),
				userRepository.findByEmail(relationshipUI.getEntityTgt()).get(0).getEntityId(),
				relationshipUI.getRelationTypeS2T(),
				relationshipUI.getRelationTypeT2S());
		Relationship r = relationshipRepository.save(relationship);
		
		Entity entitySrc = RelationshipGraph.getGraph().findEntity(relationship.getEntitySrc());
		Entity entityTgt = RelationshipGraph.getGraph().findEntity(relationship.getEntityTgt());

		entitySrc.addRelation(entityTgt, relationship.getRelationTypeS2T());
		entityTgt.addRelation(entitySrc, relationship.getRelationTypeT2S());

		Map<String, String> retValue = new HashMap<String, String>();		
		retValue.put("RefNo", r.getId());
		
		return retValue;
	}	
	
	@GetMapping(
			path = "/findbyemail",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<List<RelationshipUI>> findRelationshipByEmail(
			@RequestParam String entityIdSrc,
			@RequestParam String entityIdTgt,
			@RequestParam int maxDegreeOfRelationship)
					throws IrmNotFoundException
	{
		List<List<Relationship>> allRelationships = RelationshipGraph.getGraph().findRelationships(
				userRepository.findByEmail(entityIdSrc).get(0).getEntityId(),
				userRepository.findByEmail(entityIdTgt).get(0).getEntityId(),
				maxDegreeOfRelationship);
				
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
}
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
import com.example.irm.utils.FindRelationshipRequest;
import com.example.irm.utils.FindRelationshipResponse;
import com.example.irm.view.RelationshipUI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relationships")
public class RelationshipController {
	@Autowired
	RelationshipRepository relationshipRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	OrganizationRepository organizationRepository;

	@PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> create(@RequestBody RelationshipUI relationshipUI) throws IrmNotFoundException {
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

		Relationship relationship = new Relationship(relationshipUI.getEntitySrc(), relationshipUI.getEntityTgt(),
				relationshipUI.getRelationTypeS2T(), relationshipUI.getRelationTypeT2S());
		Relationship r = relationshipRepository.save(relationship);

		Map<String, String> retValue = new HashMap<String, String>();
		retValue.put("RefNo", r.getId());

		return retValue;
	}

	@PostMapping(path = "/registerbyemail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> createbyemail(@RequestBody RelationshipUI relationshipUI) throws IrmNotFoundException {
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

		Relationship relationship = new Relationship(entitySrcId, entityTgtId, relationshipUI.getRelationTypeS2T(),
				relationshipUI.getRelationTypeT2S());
		Relationship r = relationshipRepository.save(relationship);

		Entity entitySrc = RelationshipGraph.getGraph().findEntity(relationship.getEntitySrc());
		Entity entityTgt = RelationshipGraph.getGraph().findEntity(relationship.getEntityTgt());
		entitySrc.addRelation(entityTgt, relationshipUI.getRelationTypeS2T());
		entityTgt.addRelation(entitySrc, relationshipUI.getRelationTypeT2S());

		Map<String, String> retValue = new HashMap<String, String>();
		retValue.put("RefNo", r.getId());

		return retValue;
	}

	/*
	 * @PostMapping(path = "/find", consumes = MediaType.APPLICATION_JSON_VALUE,
	 * produces = MediaType.APPLICATION_JSON_VALUE) public String
	 * findRelationship(@RequestBody FindRelationshipRequest
	 * findRelationshipRequest) throws IrmNotFoundException, JsonProcessingException
	 * { Entity entitySrc =
	 * RelationshipGraph.getGraph().findEntity(findRelationshipRequest.
	 * getEntityIdSrc()); if (entitySrc == null) { throw new
	 * IrmNotFoundException("Not found: " +
	 * findRelationshipRequest.getEntityIdSrc()); }
	 * 
	 * Entity entityTgt =
	 * RelationshipGraph.getGraph().findEntity(findRelationshipRequest.
	 * getEntityIdTgt()); if (entityTgt == null) { throw new
	 * IrmNotFoundException("Not found: " +
	 * findRelationshipRequest.getEntityIdTgt()); }
	 * 
	 * List<List<Relationship>> allRelationships =
	 * RelationshipGraph.getGraph().findRelationships(
	 * findRelationshipRequest.getEntityIdSrc(),
	 * findRelationshipRequest.getEntityIdTgt(),
	 * findRelationshipRequest.getMaxDegreeOfRelationship());
	 * 
	 * if (allRelationships.size() > 0) { List<List<RelationshipUI>>
	 * allRelationshipUIs = new ArrayList<List<RelationshipUI>>(); for
	 * (List<Relationship> relationships : allRelationships) { List<RelationshipUI>
	 * relationshipsUI = new ArrayList<RelationshipUI>(); for (Relationship
	 * relationship : relationships) { relationshipsUI.add(new
	 * RelationshipUI(relationship.getEntitySrc(), relationship.getEntityTgt(),
	 * relationship.getRelationTypeS2T().toString(),
	 * relationship.getRelationTypeT2S().toString())); }
	 * allRelationshipUIs.add(relationshipsUI); }
	 * 
	 * FindRelationshipResponse findRelationshipResponse = new
	 * FindRelationshipResponse(allRelationshipUIs); ObjectMapper objectMapper = new
	 * ObjectMapper(); String jsonResponse =
	 * objectMapper.writeValueAsString(findRelationshipResponse);
	 * 
	 * return jsonResponse; }
	 * 
	 * throw new
	 * IrmNotFoundException("No relationship found within degree of relationship " +
	 * findRelationshipRequest.getMaxDegreeOfRelationship()); }
	 */

	@PostMapping(path = "/find", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<List<RelationshipUI>> findRelationship(@RequestBody FindRelationshipRequest findRelationshipRequest)
			throws IrmNotFoundException, JsonProcessingException {
		Entity entitySrc = RelationshipGraph.getGraph().findEntity(findRelationshipRequest.getEntityIdSrc());
		if (entitySrc == null) {
			throw new IrmNotFoundException("Not found: " + findRelationshipRequest.getEntityIdSrc());
		}

		Entity entityTgt = RelationshipGraph.getGraph().findEntity(findRelationshipRequest.getEntityIdTgt());
		if (entityTgt == null) {
			throw new IrmNotFoundException("Not found: " + findRelationshipRequest.getEntityIdTgt());
		}

		List<List<Relationship>> allRelationships = RelationshipGraph.getGraph().findRelationships(
				findRelationshipRequest.getEntityIdSrc(), findRelationshipRequest.getEntityIdTgt(),
				findRelationshipRequest.getMaxDegreeOfRelationship());

		if (allRelationships.size() > 0) {
			List<List<RelationshipUI>> allRelationshipUIs = new ArrayList<List<RelationshipUI>>();
			for (List<Relationship> relationships : allRelationships) {
				List<RelationshipUI> relationshipsUI = new ArrayList<RelationshipUI>();
				for (Relationship relationship : relationships) {
					relationshipsUI.add(new RelationshipUI(relationship.getEntitySrc(), relationship.getEntityTgt(),
							relationship.getRelationTypeS2T().toString(),
							relationship.getRelationTypeT2S().toString()));
				}
				allRelationshipUIs.add(relationshipsUI);
			}

			return allRelationshipUIs;
		}

		throw new IrmNotFoundException("No relationship found within degree of relationship "
				+ findRelationshipRequest.getMaxDegreeOfRelationship());
	}

	@PostMapping(path = "/findbyemail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String findRelationshipByEmail(@RequestBody FindRelationshipRequest findRelationshipRequest)
			throws IrmNotFoundException, JsonProcessingException {
		List<User> users = userRepository.findByEmail(findRelationshipRequest.getEntityIdSrc());
		String entitySrcId = null;
		if (users.size() > 0) {
			entitySrcId = users.get(0).getEntityId();
		} else {
			List<Organization> orgs = organizationRepository.findByEmail(findRelationshipRequest.getEntityIdSrc());
			if (orgs.size() > 0) {
				entitySrcId = orgs.get(0).getEntityId();
			} else {
				throw new IrmNotFoundException("Not found: " + findRelationshipRequest.getEntityIdSrc());
			}
		}

		users = userRepository.findByEmail(findRelationshipRequest.getEntityIdTgt());
		String entityTgtId = null;
		if (users.size() > 0) {
			entityTgtId = users.get(0).getEntityId();
		} else {
			List<Organization> orgs = organizationRepository.findByEmail(findRelationshipRequest.getEntityIdTgt());
			if (orgs.size() > 0) {
				entityTgtId = orgs.get(0).getEntityId();
			} else {
				throw new IrmNotFoundException("Not found: " + findRelationshipRequest.getEntityIdTgt());
			}
		}

		List<List<Relationship>> allRelationships = RelationshipGraph.getGraph().findRelationships(entitySrcId,
				entityTgtId, findRelationshipRequest.getMaxDegreeOfRelationship());

		if (allRelationships.size() > 0) {
			List<List<RelationshipUI>> allRelationshipUIs = new ArrayList<List<RelationshipUI>>();
			for (List<Relationship> relationships : allRelationships) {
				List<RelationshipUI> relationshipsUI = new ArrayList<RelationshipUI>();
				for (Relationship relationship : relationships) {
					relationshipsUI.add(new RelationshipUI(relationship.getEntitySrc(), relationship.getEntityTgt(),
							relationship.getRelationTypeS2T().toString(),
							relationship.getRelationTypeT2S().toString()));
				}
				allRelationshipUIs.add(relationshipsUI);
			}

			FindRelationshipResponse findRelationshipResponse = new FindRelationshipResponse(allRelationshipUIs);
			ObjectMapper mapper = new ObjectMapper();
			String jsonResponse = mapper.writeValueAsString(findRelationshipResponse);

			return jsonResponse;
		}

		throw new IrmNotFoundException("No relationship found within degree of relationship "
				+ findRelationshipRequest.getMaxDegreeOfRelationship());
	}

	@GetMapping(path = "/findall", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RelationshipUI> findAll() {
		List<Relationship> relationships = relationshipRepository.findAll();
		List<RelationshipUI> relationshipsUI = new ArrayList<>();

		for (Relationship relationship : relationships) {
			relationshipsUI.add(new RelationshipUI(relationship.getEntitySrc(), relationship.getEntityTgt(),
					relationship.getRelationTypeS2T().toString(), relationship.getRelationTypeT2S().toString()));
		}

		return relationshipsUI;
	}
}
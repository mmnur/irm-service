package com.example.irm.controller;

import com.example.irm.error.IrmCryptographyException;
import com.example.irm.error.IrmNotFoundException;
import com.example.irm.graph.Entity;
import com.example.irm.graph.RelationshipGraph;
import com.example.irm.model.Device;
import com.example.irm.model.Organization;
import com.example.irm.model.Relationship;
import com.example.irm.model.User;
import com.example.irm.repository.DeviceRepository;
import com.example.irm.repository.OrganizationRepository;
import com.example.irm.repository.RelationshipRepository;
import com.example.irm.repository.UserRepository;
import com.example.irm.utils.EntityType;
import com.example.irm.utils.PkiUtil;
import com.example.irm.view.DeviceUI;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

	@Autowired
	RelationshipRepository relationshipRepository;
	
	@PostMapping(
			path = "/devices/initreg", 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> init_register(@RequestBody DeviceUI deviceUI)
			throws IrmNotFoundException
	{
		List<User> users = userRepository.findByEntityId(deviceUI.getOwnerEID());
		if (users.size() < 1) {
			List<Organization> orgs = orgRepository.findByEntityId(deviceUI.getOwnerEID());
			if (orgs.size() < 1) {
				throw new IrmNotFoundException("Owner not found: " + deviceUI.getOwnerEID());
			}
		}
		Device device = deviceRepository.save(new Device(deviceUI.getDisplayName(), deviceUI.getOwnerEID(), deviceUI.getPublicKey()));
		
		Map<String, String> retValue = new HashMap<String, String>();
		retValue.put("challenge", device.getChallenge());
		
		return retValue;	
	}
	
	@PostMapping(path = "/devices/finishreg",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> finish_register(@RequestBody DeviceUI device)
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
		deviceToUpdate = deviceRepository.save(deviceToUpdate);

		String deviceEid = deviceToUpdate.getEntityId();
		Entity deviceEntity = new Entity(deviceEid, EntityType.Device); 
		RelationshipGraph.getGraph().addEntity(deviceEntity);

		String ownerEid = deviceToUpdate.getOwnerEID();		
		Entity ownerEntity = RelationshipGraph.getGraph().findEntity(ownerEid);
		if (ownerEntity != null) {
			ownerEntity.addRelation(deviceEntity, "Owner");
			deviceEntity.addRelation(ownerEntity, "OwnedBy");			
		}
		
		Relationship relationship = new Relationship(ownerEid, ownerEid, "Owner", "OwnedBy");
		relationshipRepository.save(relationship);

		Map<String, String> retValue = new HashMap<String, String>();		
		retValue.put("entityId", deviceEid);
		
		return retValue;		
	}
}
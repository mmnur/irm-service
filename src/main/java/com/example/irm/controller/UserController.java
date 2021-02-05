package com.example.irm.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.irm.error.IrmAlreadyExistsException;
import com.example.irm.error.IrmNotFoundException;
import com.example.irm.graph.Entity;
import com.example.irm.graph.RelationshipGraph;
import com.example.irm.model.User;
import com.example.irm.repository.UserRepository;
import com.example.irm.utils.EntityType;
import com.example.irm.view.UserUI;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
 
 
@RestController
@RequestMapping("/users")
public class UserController
{
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(path = "/bulkregister")
	public Iterable<User> bulkcreate()
	{
		 Iterable<User> users = userRepository.saveAll(Arrays.asList(
        		  new User("Jim Carter", "jim1@example.com", "jim1", "jim123")
        		, new User("John Doe", "john1@example.com", "john1", "john123")
        		, new User("Bobby Jones", "bobby1@example.com", "bobby1", "bobby123")
        		, new User("Alexa Thompson", "alexa1@example.com", "alexa1", "alexa123")
        		, new User("Tom Watson", "tom1@example.com", "tom1", "tom123")
        		, new User("Sofia Watson", "sofia1@example.com", "sofia1", "sofia123")
        		, new User("Sara Reed", "sara1@example.com", "sara1", "sara123")
        		, new User("Jack Smith", "jack1@example.com", "jack1", "jack123")
        		, new User("Pamela Smith", "pam1@example.com", "pam1", "pam123")
        		, new User("Oliver Smith", "oliver1@example.com", "oliver1", "oliver123")));
		
		for (User user: users) {
			String eid = user.getEntityId();
			Entity entity = new Entity(eid, EntityType.User); 
			RelationshipGraph.getGraph().addEntity(entity);
		}

		return users;
	}
	
	@PostMapping(
			path = "/register",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> create(@RequestBody UserUI userUI)
			throws IrmAlreadyExistsException
	{
		List<User> users = userRepository.findByUsername(userUI.getUsername());		
		if (!users.isEmpty()) {
			throw new IrmAlreadyExistsException("A user already exists with the same username");
		}
		
		users = userRepository.findByEmail(userUI.getEmail());
		if (!users.isEmpty()) {
			throw new IrmAlreadyExistsException("A user already exists with the same email");
		}
		
		User user = userRepository.save(new User(userUI.getDisplayName(), userUI.getEmail(), userUI.getUsername(), userUI.getPassword()));
		String eid = user.getEntityId();
		Entity entity = new Entity(eid, EntityType.User); 
		RelationshipGraph.getGraph().addEntity(entity);

		Map<String, String> retValue = new HashMap<String, String>();		
		retValue.put("entityId", eid);
		
		return retValue;
	}
	
	@PostMapping(
			path = "/authenticate",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> authenticate(@RequestBody UserUI userUI)
			throws IrmNotFoundException
	{
		List<User> users = userRepository.findByUsername(userUI.getUsername());		
		if (users.isEmpty()) {
			throw new IrmNotFoundException("Username or password not found!");
		}
		
		User user = users.get(0);
		String password = user.getPassword();
		if (userUI.getPassword().equals(password)) {
			Map<String, String> retValue = new HashMap<String, String>();		
			retValue.put("entityId", user.getEntityId());			
			return retValue;			
		}
		throw new IrmNotFoundException("Username or password not found!");
	}
	
	@GetMapping(path = "/findall")
	public List<UserUI> findAll()
	{
		List<User> users = userRepository.findAll();
		List<UserUI> userUI = new ArrayList<>();
		
		for (User user : users) {
			userUI.add(new UserUI(user.getEntityId(), user.getDisplayName(), user.getEmail(), user.getUsername()));
		}

		return userUI;
	}
}
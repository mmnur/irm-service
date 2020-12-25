package com.example.irm.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.irm.model.User;
import com.example.irm.repository.UserRepository;
import com.example.irm.view.UserUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
 
@RestController
public class UserController
{
	@Autowired
	UserRepository repository;
	
	@GetMapping(path = "/users/bulkregister")
	public String bulkcreate()
	{
		// save a single User
		repository.save(new User("John Doe", "john1@example.com", "john1", "john123"));
		
		// save a list of Users
        repository.saveAll(Arrays.asList(
        		new User("Jim Carter", "jim1@example.com", "jim1", "jim123")
        		, new User("Jack Smith", "jack1@example.com", "jack1", "jack123")
        		, new User("Pam Smith", "pam1@example.com", "pam1", "pam123")
        		, new User("Oliver Smith", "oliver1@example.com", "oliver1", "oliver123")));
		
		return "Demo users are created";
	}
	
	@PostMapping(path = "/users/register", consumes = "application/json")
	public String create(@RequestBody UserUI user)
	{
		// save a single User
		repository.save(new User(user.getDisplayName(), user.getEmail(), user.getUsername(), user.getPassword()));		

		return "EID = " + repository.findByEmail(user.getEmail()).get(0).getEntityId();
	}
	
	@GetMapping(path = "/users/findall")
	public List<UserUI> findAll()
	{
		List<User> users = repository.findAll();
		List<UserUI> userUI = new ArrayList<>();
		
		for (User user : users) {
			userUI.add(new UserUI(user.getEntityId(), user.getDisplayName(), user.getEmail(), user.getUsername()));
		}

		return userUI;
	}
	
	@RequestMapping(path = "/users/eid/{entityId}")
	public String searchByEntityId(@PathVariable String entityId){
		String user = "";
		user = repository.findByEntityId(entityId).toString();
		return user;
	}
	
	@RequestMapping(path = "/users/email/{email}")
	public String searchByEmail(@PathVariable String email){
		String user = "";
		user = repository.findByEmail(email).toString();
		return user;
	}

	@RequestMapping(path = "/users/username/{username}")
	public List<UserUI> fetchDataByUsername(@PathVariable String username){
	
		List<User> users = repository.findByUsername(username);
		List<UserUI> userUI = new ArrayList<>();
		
		for (User user : users) {
			userUI.add(new UserUI(user.getDisplayName(), user.getEmail(), user.getUsername(), user.getPassword()));
		}

		return userUI;
	}
}
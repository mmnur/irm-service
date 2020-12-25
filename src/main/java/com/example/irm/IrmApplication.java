package com.example.irm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.irm.repository.UserRepository;

@SpringBootApplication
public class IrmApplication {

	@Autowired
	UserRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(IrmApplication.class, args);
	}



}


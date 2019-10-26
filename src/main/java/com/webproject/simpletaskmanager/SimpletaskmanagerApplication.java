package com.webproject.simpletaskmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.webproject.simpletaskmanager.entities.Useraccount;
import com.webproject.simpletaskmanager.repositories.UseraccountRepository;

@SpringBootApplication
public class SimpletaskmanagerApplication implements CommandLineRunner {
	
	@Autowired
	UseraccountRepository repos;
	
	public static void main(String[] args) {
		SpringApplication.run(SimpletaskmanagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (Useraccount u : repos.findAll()) {
			System.out.println(u);
		}
		System.out.println(repos.count());
	}
}

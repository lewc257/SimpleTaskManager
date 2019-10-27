package com.webproject.simpletaskmanager;

import java.sql.Timestamp;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.webproject.simpletaskmanager.entities.Task;
import com.webproject.simpletaskmanager.entities.UserInfo;
import com.webproject.simpletaskmanager.entities.Useraccount;
import com.webproject.simpletaskmanager.repositories.UseraccountRepository;
import com.webproject.simpletaskmanager.repositoriesimpl.UseraccountRepositoryImpl;

@SpringBootApplication
@Transactional
public class SimpletaskmanagerApplication implements CommandLineRunner {
	
	@Autowired
	UseraccountRepositoryImpl repos;
	
	public static void main(String[] args) {
		SpringApplication.run(SimpletaskmanagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		addUserInfoExample();
	}
	
	public void addUserInfoExample() {
		Useraccount user = repos.findUseraccountById(1);
		UserInfo info = new UserInfo();
		info.setFirstName("testFirst");
		info.setLastName("testLast");
		info.setPersonalEmail("test@test.com");
		info.setCreated(new Timestamp(new Date().getTime()));
		user.setUserInfo(info);
		user = repos.saveAccount(user);
		if (user.getId() != null) {
			System.out.println("Success");
		}
	}
	
	public void addTaskExample() {
		Useraccount user = repos.findUseraccountById(1);
		Task task = new Task();
		task.setName("testtask2");
		task.setStatus(false);
		task.setCreated(new Timestamp(new Date().getTime()));
		user.addTask(task);
		user = repos.saveAccount(user);
		System.out.println(user);
	}
	
}

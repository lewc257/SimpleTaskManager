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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.webproject.simpletaskmanager.entities.Task;
import com.webproject.simpletaskmanager.entities.UserInfo;
import com.webproject.simpletaskmanager.entities.Useraccount;
import com.webproject.simpletaskmanager.repositories.TaskRepository;
import com.webproject.simpletaskmanager.repositories.UserRepository;
import com.webproject.simpletaskmanager.repositories.UseraccountDAOLocal;
import com.webproject.simpletaskmanager.repositoriesdao.TaskDAO;
import com.webproject.simpletaskmanager.repositoriesdao.UseraccountDAO;


@SpringBootApplication
public class SimpletaskmanagerApplication implements CommandLineRunner {
	
	@Autowired
	UserRepository uRepos;
	
	@Autowired
	TaskRepository tRepos;
	
	public static void main(String[] args) {
		SpringApplication.run(SimpletaskmanagerApplication.class, args);
	}
	
	
	@Transactional
	@Override
	public void run(String... args) throws Exception {
		//tRepos.deleteTaskById(32);
		//tRepos.updateStatus(true, 31);
	}
	
}

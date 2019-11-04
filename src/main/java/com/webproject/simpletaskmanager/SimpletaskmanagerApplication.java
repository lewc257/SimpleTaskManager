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
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.webproject.simpletaskmanager.entities.Task;
import com.webproject.simpletaskmanager.entities.UserInfo;
import com.webproject.simpletaskmanager.entities.Useraccount;
import com.webproject.simpletaskmanager.repositories.TaskRepository;
import com.webproject.simpletaskmanager.repositories.UserRepository;


@SpringBootApplication
public class SimpletaskmanagerApplication implements CommandLineRunner {
	
	@Autowired
	UserRepository uRepos;
	
	@Autowired
	TaskRepository tRepos;
	
	public static void main(String[] args) {
		SpringApplication.run(SimpletaskmanagerApplication.class, args);
	}
	
	/*
	 * https://www.baeldung.com/spring-custom-validation-message-source
	 */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource
		= new ReloadableResourceBundleMessageSource();
		
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	@Bean
	public LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}
	
	
	@Transactional
	@Override
	public void run(String... args) throws Exception {

	}
	
}

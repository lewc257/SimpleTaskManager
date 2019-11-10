package com.webproject.simpletaskmanager;

import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@SpringBootApplication
public class SimpletaskmanagerApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(SimpletaskmanagerApplication.class, args);
	}
	
	/*
	 * https://www.baeldung.com/spring-custom-validation-message-source
	 * 
	 * https://www.baeldung.com/spring-boot-internationalization
	 * 
	 * https://www.journaldev.com/2668/spring-validation-example-mvc-validator
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
	
	@Bean
	public LocaleResolver localResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.getDefault());
		return slr;
	}
	
	
	@Transactional
	@Override
	public void run(String... args) throws Exception {
	}
	
}

package com.webproject.simpletaskmanager.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.webproject.simpletaskmanager.entities.Useraccount;
import com.webproject.simpletaskmanager.repositories.UserRepository;

public class UserValidator implements Validator{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Useraccount.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//Username
		//Password
		//first name
		///last name
		//personal email
	}

}

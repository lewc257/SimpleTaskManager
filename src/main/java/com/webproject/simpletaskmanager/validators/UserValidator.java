package com.webproject.simpletaskmanager.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webproject.simpletaskmanager.entities.Useraccount;
import com.webproject.simpletaskmanager.repositories.UserRepository;

@Component
public class UserValidator implements Validator{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Useraccount.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Useraccount user = (Useraccount) target;
		//Username
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.required");
		//Password
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
		//first name
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userInfo.firstName", "firstname.required");
		///last name
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userInfo.lastName", "lastName.required");
		//personal email
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userInfo.personalEmail", "personalEmail.required");
	}

}

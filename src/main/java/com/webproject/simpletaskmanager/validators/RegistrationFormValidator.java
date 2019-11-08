package com.webproject.simpletaskmanager.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webproject.simpletaskmanager.forms.RegistrationForm;
import com.webproject.simpletaskmanager.repositories.UserInfoRepository;
import com.webproject.simpletaskmanager.repositories.UserRepository;

@Component
public class RegistrationFormValidator implements Validator{
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public UserInfoRepository userInfoRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return RegistrationForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RegistrationForm form = (RegistrationForm) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.required");
		if (userRepository.findByUsername(form.getUsername()) != null){
			errors.rejectValue("username", "username.exists");
		}
		
		if (userInfoRepository.findByEmail(form.getPersonalEmail()) != null){
			errors.rejectValue("personalEmail", "personalEmail.exists");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
		if (form.getPassword().length() < 8) {
			errors.rejectValue("password", "password.invalidLength");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.required");
		if (!form.getFirstName().matches("^[A-Z][a-zA-Z]+")) {
			errors.rejectValue("firstName", "firstName.mismatch");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName","lastName.required");
		if (!form.getLastName().matches("^[A-Z][a-zA-Z]+")) {
			errors.rejectValue("lastName", "lastName.mismatch");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "personalEmail", "personalEmail.required");
		if (!form.getPersonalEmail().matches(Constants.EMAIL_REGEX)) {
			errors.rejectValue("personalEmail", "personalEmail.mismatch");
		}
	}

}

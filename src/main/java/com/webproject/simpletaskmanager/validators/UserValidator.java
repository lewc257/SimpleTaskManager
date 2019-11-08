package com.webproject.simpletaskmanager.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webproject.simpletaskmanager.entities.Useraccount;
import com.webproject.simpletaskmanager.repositories.UserInfoRepository;
import com.webproject.simpletaskmanager.repositories.UserRepository;

@Component
public class UserValidator implements Validator{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Useraccount.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Useraccount user = (Useraccount) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.required");
//		Integer usernameCount = userRepository.usernameCount(user.getUsername());
//		if (usernameCount != null && usernameCount > 1){
//			errors.rejectValue("username", "username.exists");
//		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
		if (user.getPassword().length() < Constants.PASSWORD_MIN_LEN) {
			errors.rejectValue("password", "password.invalidLength");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userInfo.firstName", "firstname.required");
		if (!user.getUserInfo().getFirstName().matches(Constants.FIRSTNAME_REGEX)) {
			errors.rejectValue("userInfo.firstName", "firstName.mismatch");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userInfo.lastName", "lastName.required");
		if (!user.getUserInfo().getLastName().matches(Constants.LASTNAME_REGEX)) {
			errors.rejectValue("userInfo.lastName", "lastName.mismatch");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userInfo.personalEmail", "personalEmail.required");
//		Integer emailCount = userInfoRepository.emailCount(user.getUserInfo().getPersonalEmail());
//		if (emailCount != null && emailCount > 1){
//			errors.rejectValue("userInfo.personalEmail", "personalEmail.exists");
//		}
		if (!user.getUserInfo().getPersonalEmail().matches(Constants.EMAIL_REGEX)) {
			errors.rejectValue("userInfo.personalEmail", "personalEmail.mismatch");
		}
	}

}

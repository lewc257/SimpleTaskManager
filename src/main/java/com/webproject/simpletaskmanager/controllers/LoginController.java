package com.webproject.simpletaskmanager.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webproject.simpletaskmanager.entities.UserInfo;
import com.webproject.simpletaskmanager.entities.Useraccount;
import com.webproject.simpletaskmanager.forms.LoginForm;
import com.webproject.simpletaskmanager.forms.RegistrationForm;
import com.webproject.simpletaskmanager.repositories.UserRepository;
import com.webproject.simpletaskmanager.validators.LoginFormValidator;
import com.webproject.simpletaskmanager.validators.RegistrationFormValidator;

/**
 * 
 * @author Lewis
 *
 */
@Controller("/login")
public class LoginController{

	@Autowired
	UserRepository userRepository;
	
	/*
	 * very useful, because most validators are singletons + unit test mocking becomes easier + 
	 * your validator could call other Spring components.
	 */
	@Autowired 
	LoginFormValidator loginFormValidator;
	
	@Autowired
	RegistrationFormValidator registrationFormValidator;

	/*
	 * Loads the login page upon request
	 */
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		model.addAttribute("all", userRepository.findAll());
		return "login";
	}
	
	
	/*
	 * verify's the user once the form has been submitted
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@ModelAttribute("loginForm")  LoginForm loginForm, Model model, 
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		loginFormValidator.validate(loginForm, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "login";
		}

		Useraccount user = userRepository.findByUsername(loginForm.getUsername());
		
		Map<String, String> messages = new HashMap<String, String>();
		
		if (user == null) {
			messages.put("usernameInvalid", "Username is invalid");
		} else {
			boolean passwordIsValid = user.getPassword().equals(loginForm.getPassword());
			if (!passwordIsValid){
				messages.put("passwordInvalid", "Password is invalid");
			}
		}
		if (!messages.isEmpty()) {
			model.addAllAttributes(messages);
			return "login";
		}
		redirectAttributes.addFlashAttribute("user", user);
		return "redirect:/dashboard";
	}
	
	
	/*
	 * Navigates to the user registration page upon request
	 */
	@RequestMapping(value="/registration", method=RequestMethod.GET)
	public String registrationPage(Model model) {
		model.addAttribute("registrationForm", new RegistrationForm());
		return "user_registration";
	}
	
	
	/*
	 * Creates a new user once the registration form has been submitted
	 */
	@RequestMapping(value="/registration", method=RequestMethod.POST)
	public String register(@ModelAttribute("registrationForm")RegistrationForm form, HttpServletResponse response, BindingResult bindingResult, Model model) {
		
		registrationFormValidator.validate(form, bindingResult);
		if (bindingResult.hasErrors()) {
			return "user_registration";
		}
	
		String username = form.getUsername();
		String password = form.getPassword();
		String firstName = form.getFirstName();
		String lastName = form.getLastName();
		String personalEmail = form.getPersonalEmail();
	
		Timestamp created = new Timestamp(new Date().getTime());
		Useraccount user = new Useraccount();
		user.setUsername(username);
		user.setPassword(password);
		user.setCreated(created);
		
		UserInfo userInfo = new UserInfo();
		userInfo.setFirstName(firstName);
		userInfo.setLastName(lastName);
		userInfo.setPersonalEmail(personalEmail);
		userInfo.setCreated(created);
		user.setUserInfo(userInfo);
		
		userRepository.save(user);
		System.out.println(user + " has been created");
		return "redirect:/login";
	}
}

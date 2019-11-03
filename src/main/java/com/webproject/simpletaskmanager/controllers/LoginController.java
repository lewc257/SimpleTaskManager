package com.webproject.simpletaskmanager.controllers;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import com.webproject.simpletaskmanager.repositoriesdao.UseraccountDAO;

/**
 * 
 * @author Lewis
 *
 */
@Controller("/login")
public class LoginController {

	@Autowired
	UserRepository userRepository;

	/*
	 * Loads the login page upon request
	 */
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginPage(Model model) {
		return "login";
	}
	
	
	/*
	 * verify's the user once the form has been submitted
	 */
	@RequestMapping(value="/dashboard", method=RequestMethod.POST)
	public String verifyUser(@ModelAttribute("loginForm") LoginForm loginForm, Model model, 
			RedirectAttributes redirectAttributes) {
		Useraccount user = userRepository.findUseraccount(loginForm.getUsername(), loginForm.getPassword());
		if (user == null) {
			redirectAttributes.addFlashAttribute("invalidCredentials", true);
			return "redirect:/login";
		}
		redirectAttributes.addFlashAttribute("user", user);
		return "redirect:/dashboard";
	}
	
	
	/*
	 * Navigates to the user registration page upon request
	 */
	@RequestMapping(value="/registration", method=RequestMethod.GET)
	public String registrationPage(Model model) {
		return "user_registration";
	}
	
	
	/*
	 * Creates a new user once the registration form has been submitted
	 */
	@RequestMapping(value="/registerUser", method=RequestMethod.POST)
	public String register(@ModelAttribute("registrationForm")RegistrationForm form, Model model) {
		String username = form.getUsername();
		String password = form.getPassword();
		String firstName = form.getFirstName();
		String lastName = form.getLastName();
		String personalEmail = form.getPersonalEmail();
		//TODO: Validation
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

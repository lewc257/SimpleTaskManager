package com.webproject.simpletaskmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.webproject.simpletaskmanager.entities.Useraccount;
import com.webproject.simpletaskmanager.forms.LoginForm;
import com.webproject.simpletaskmanager.repositoriesdao.UseraccountDAO;

@Controller
public class LoginController {
	
	@Autowired
	UseraccountDAO useraccountDAO;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginPage(Model model) {
		model.addAttribute("users", useraccountDAO.findAll());
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String resultPage(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
		String username = loginForm.getUsername();
		String password = loginForm.getPassword();
		Useraccount user = useraccountDAO.findUseraccount(username, password);

		if (user == null) {
			model.addAttribute("invalidCredentials", true);
			return "login";
		}
		model.addAttribute("loggedInUser", user);
		return "dashboard";
	}
}

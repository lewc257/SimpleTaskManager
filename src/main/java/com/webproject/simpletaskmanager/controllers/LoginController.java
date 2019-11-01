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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webproject.simpletaskmanager.entities.Useraccount;
import com.webproject.simpletaskmanager.forms.LoginForm;
import com.webproject.simpletaskmanager.repositories.UserRepository;
import com.webproject.simpletaskmanager.repositoriesdao.UseraccountDAO;

@Controller("/login")
public class LoginController {

	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginPage(Model model) {
		return "login";
	}
	
	@RequestMapping(value="/dashboard", method=RequestMethod.POST)
	public String resultPage(@ModelAttribute("loginForm") LoginForm loginForm, Model model, 
			RedirectAttributes redirectAttributes) {
		Useraccount user = userRepository.findUseraccount(loginForm.getUsername(), loginForm.getPassword());
		if (user == null) {
			model.addAttribute("invalidCredentials", true);
			return "login";
		}
		redirectAttributes.addFlashAttribute("user", user);
		return "redirect:/dashboard";
	}
}

package com.webproject.simpletaskmanager.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webproject.simpletaskmanager.repositoriesdao.TaskDAO;
import com.webproject.simpletaskmanager.repositoriesdao.UseraccountDAO;

@Controller
public class MainController {
	
	@Autowired
	TaskDAO taskDAO;
	
	@Autowired
	UseraccountDAO useraccountDAO;
	
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public String dashboardPage(HttpServletRequest request, Model model) {
		
		return "dashboard";
	}
}

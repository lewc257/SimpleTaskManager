package com.webproject.simpletaskmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webproject.simpletaskmanager.repositoriesdao.TaskDAO;
import com.webproject.simpletaskmanager.repositoriesdao.UseraccountDAO;

@Controller
@RequestMapping("/")
public class MainViewController {
	
	@Autowired
	TaskDAO taskDAO;
	
	@Autowired
	UseraccountDAO useraccountDAO;
	
	@RequestMapping("/tests")
	public String test(Model model) {
		model.addAttribute("tasks", taskDAO.findAll());
		model.addAttribute("users", useraccountDAO.findAll());
		return "tests";
	}
}

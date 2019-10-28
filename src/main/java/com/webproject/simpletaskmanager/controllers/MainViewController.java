package com.webproject.simpletaskmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainViewController {
	
	@GetMapping("/tasks")
	public String test(Model model) {
		return "tasks";
	}
}

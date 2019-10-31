package com.webproject.simpletaskmanager.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.webproject.simpletaskmanager.entities.Task;
import com.webproject.simpletaskmanager.entities.Useraccount;
import com.webproject.simpletaskmanager.repositoriesdao.TaskDAO;
import com.webproject.simpletaskmanager.repositoriesdao.UseraccountDAO;

@Controller("/dashboard")
@SessionAttributes("user")
@Transactional
public class MainController {
	
	@Autowired
	TaskDAO taskDAO;
	
	@Autowired
	UseraccountDAO useraccountDAO;
	
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public String dashboardPage(HttpServletRequest request, Model model) {
		Useraccount user = (Useraccount) model.asMap().get("user");
		System.out.println("Current User = " + user);
		model.addAttribute("user", user);
		return "dashboard";
	}
	
	@RequestMapping(value="/dashboard/addTask", method=RequestMethod.POST)
	public String addTask(@ModelAttribute("name") String taskName, @ModelAttribute("user") Useraccount user,
			SessionStatus status, Model model) {
		Task task = new Task();
		task.setName(taskName);
		task.setStatus(false);
		task.setCreated(new Timestamp(new Date().getTime()));
		user.addTask(task);
		useraccountDAO.saveAccount(user);
		System.out.println("Successful");
		return "redirect:/dashboard";
	}
	
	@RequestMapping(value="/dashboard/deleteTask/{id}", method=RequestMethod.GET)
	public String deleteTask(@PathVariable("id") String taskId, @ModelAttribute("user") Useraccount user) {
		System.out.printf("Selected taskId=%s, userId=%d", taskId, user.getId());
		return "redirect:/dashboard";
	}
	
	//TODO: SEARCH
	
	//TODO: EDIT
	
	@RequestMapping(value="/dashboard/endsession", method=RequestMethod.POST)
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:/login";
	}
}


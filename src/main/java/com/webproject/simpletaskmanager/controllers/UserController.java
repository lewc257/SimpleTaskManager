package com.webproject.simpletaskmanager.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.webproject.simpletaskmanager.entities.Task;
import com.webproject.simpletaskmanager.entities.UserInfo;
import com.webproject.simpletaskmanager.entities.Useraccount;
import com.webproject.simpletaskmanager.forms.UserEditForm;
import com.webproject.simpletaskmanager.repositories.TaskRepository;
import com.webproject.simpletaskmanager.repositories.UserRepository;
import com.webproject.simpletaskmanager.repositoriesdao.TaskDAO;
import com.webproject.simpletaskmanager.repositoriesdao.UseraccountDAO;

@Transactional
@Controller("/dashboard")
@SessionAttributes("user")
public class UserController {	
	/**
	 * TODO: FILTER BY NAME, FILTER BY DATE, SORT BY DATE
	 * TODO: EDIT
	 */
	@Autowired
	TaskDAO taskDAO;
	
	@Autowired
	UseraccountDAO useraccountDAO;
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public String dashboardPage(HttpServletRequest request, Model model) {
		Useraccount user = (Useraccount) model.asMap().get("user");
		System.out.println("Logged in user = " + user);
		model.addAttribute("user", user);
		return "dashboard";
	}
	
	//Creates a new Task
	@RequestMapping(value="/addTask", method=RequestMethod.POST)
	public String addTask(@ModelAttribute("name") String taskName, @SessionAttribute("user") Useraccount user,
			SessionStatus status, Model model) {
		Task task = new Task();
		task.setName(taskName);
		task.setStatus(false);
		task.setCreated(new Timestamp(new Date().getTime()));
		user.addTask(task);
		taskRepository.save(task);
		System.out.println("Added new task to " + user);
		return "dashboard";
	}
	
	//Deletes a task
	@RequestMapping(value="/deleteTask/{id}", method=RequestMethod.GET)
	public String deleteTask(@PathVariable("id") Integer taskId, @SessionAttribute("user") Useraccount user, SessionStatus status, Model model) {
		Task existingTask = taskDAO.findTaskById(taskId);
		if (user.removeTask(existingTask)) {
			taskRepository.deleteTaskById(taskId);
		}
		return "redirect:/dashboard";
	}
	
	//Edits the user info
	@RequestMapping(value="/user_edit_form", method=RequestMethod.GET)
	public String editPage(Model model, @SessionAttribute Useraccount user) {
		UserEditForm userEditForm = new UserEditForm();
		userEditForm.setFirstName(user.getUserInfo().getFirstName());
		userEditForm.setLastName(user.getUserInfo().getLastName());
		userEditForm.setUsername(user.getUsername());
		userEditForm.setPassword(user.getPassword());
		userEditForm.setPersonalEmail(user.getUserInfo().getPersonalEmail());
		model.addAttribute("userInfo", userEditForm);
		return "user_edit_form";
	}
	
	
	@RequestMapping(value="/updateUser", method=RequestMethod.POST)
	public String editUser(@ModelAttribute("userInfo") UserEditForm userEditForm, @SessionAttribute Useraccount user, Model model) {
		System.out.println(userEditForm);
		return "user_edit_form";
	}
	
	
	//Logs the user out
	@RequestMapping(value="/endsession", method=RequestMethod.POST)
	public String logout(SessionStatus status, @SessionAttribute("user") Useraccount user) {
		status.setComplete();
		System.out.println(user + " has logged out");
		return "redirect:/login";
	}
}


package com.webproject.simpletaskmanager.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webproject.simpletaskmanager.entities.Task;
import com.webproject.simpletaskmanager.entities.UserInfo;
import com.webproject.simpletaskmanager.entities.Useraccount;
import com.webproject.simpletaskmanager.forms.RegistrationForm;
import com.webproject.simpletaskmanager.repositories.TaskRepository;
import com.webproject.simpletaskmanager.repositories.UserRepository;
import com.webproject.simpletaskmanager.validators.UserValidator;


/**
 * 
 * @author Lewis
 *
 */
@Transactional
@Controller("/dashboard")
@SessionAttributes("user")
public class UserController {	
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserValidator userValidator;
	
	/*
	 * Loads the dashboard upon request
	 */
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public String dashboardPage(HttpServletRequest request, SessionStatus status, Model model) {
		Useraccount user = (Useraccount) model.asMap().get("user");
		model.addAttribute("user", user);
		return "dashboard";
	}
	
	/*
	 * Creates a new task
	 */
	@RequestMapping(value="/addTask", method=RequestMethod.POST)
	public String addTask(@ModelAttribute("name") String taskName, @SessionAttribute("user") Useraccount loggedInUser, Model model) {
		//TODO: Check for empty name
		Task task = new Task();
		task.setName(taskName);
		task.setStatus(false);
		task.setCreated(new Timestamp(new Date().getTime()));
		loggedInUser.addTask(task);
		taskRepository.save(task);
		//TODO: Log save
		return "redirect:/dashboard";
	}
	

	/*
	 * Deletes a task
	 */
	@RequestMapping(value="/deleteTask/{id}", method=RequestMethod.GET)
	public String deleteTask(@PathVariable("id") Integer taskId, @SessionAttribute("user") Useraccount loggedInUser) {
		Task existingTask = taskRepository.findTaskById(taskId);
		if (loggedInUser.removeTask(existingTask)) {
			taskRepository.deleteTaskById(taskId);
			//TODO: Log delete
		}
		return "redirect:/dashboard";
	}
	
	
	/*
	 * Navigates to task edit page upon request
	 */
	@RequestMapping(value="/editTask/{id}", method=RequestMethod.GET)
	public String editTaskPage(@PathVariable("id") Integer taskId, @SessionAttribute("user") Useraccount loggedInUser, Model model) {
		Task existingTask = taskRepository.findTaskById(taskId);
		model.addAttribute("selectedTask", existingTask);
		return "edit_task";
	}
	
	
	/*
	 * Updates a task
	 */
	@RequestMapping(value="/editTask/edit", method=RequestMethod.POST)
	public String editTask(@ModelAttribute("selectedTask") Task editedTask, @SessionAttribute("user") Useraccount loggedInUser) {
		//TODO: Check for empty 	name
		loggedInUser.mergeTask(editedTask);
		taskRepository.save(editedTask);
		//TODO: Log save
		return "redirect:/dashboard";
	}
	

	/*
	 * Navigates to the user's edit form upon request
	 */
	@RequestMapping(value="/user-edit", method=RequestMethod.GET)
	public String editUserPage(Model model, @SessionAttribute Useraccount user) {
		model.addAttribute("userInfo", user);
		return "user_edit_form";
	}
	
	
	/*
	 * Updates the user
	 */
	@RequestMapping(value="/user-edit", method=RequestMethod.POST)
	public String editUser(@ModelAttribute("userInfo") Useraccount userEdit, @SessionAttribute("user") Useraccount loggedInUser, 
			BindingResult bindingResult, Model model) {
		
		userValidator.validate(userEdit, bindingResult);
		if (bindingResult.hasErrors()) {
			return "user_edit_form";
		}
			
		String username = userEdit.getUsername();
		String password = userEdit.getPassword();
		String firstName = userEdit.getUserInfo().getFirstName();
		String lastName = userEdit.getUserInfo().getLastName();
		String personalEmail = userEdit.getUserInfo().getPersonalEmail();

		loggedInUser.setUsername(username);
		loggedInUser.setPassword(password);
		loggedInUser.getUserInfo().setFirstName(firstName);
		loggedInUser.getUserInfo().setLastName(lastName);
		loggedInUser.getUserInfo().setPersonalEmail(personalEmail);

		userRepository.save(loggedInUser);
		//TODO: Log save
		return "redirect:/dashboard";
	}
	
	/*
	 * TODO:Filters a task by name
	 */
	
	/*
	 * TODO:Sorts the tasks by name
	 */
	
	/*
	 * TODO:Sorts the tasks by date
	 */
	
	/*
	 * Logs the user out upon request
	 */
	@RequestMapping(value="/endsession", method=RequestMethod.POST)
	public String logout(SessionStatus status, @SessionAttribute("user") Useraccount loggedInUser) {
		status.setComplete();
		//TODO: Log status
		return "redirect:/login";
	}
}


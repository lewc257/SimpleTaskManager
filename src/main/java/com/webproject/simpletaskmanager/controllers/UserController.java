package com.webproject.simpletaskmanager.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;
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
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addTask(@ModelAttribute("name") String taskName, BindingResult bindingResult,
			@SessionAttribute("user") Useraccount loggedInUser, RedirectAttributes redirect, Model model) {
		
		if (taskName == null || taskName.isEmpty()) {
			redirect.addFlashAttribute("taskNameError", true);
			return "redirect:/dashboard";
		}
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
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
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
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String editTaskPage(@PathVariable("id") Integer taskId, @SessionAttribute("user") Useraccount loggedInUser, Model model) {
		Optional<Task> existingTask = loggedInUser.getTasks().stream().filter(t -> t.getId() == taskId).findFirst();
		if (existingTask.isPresent()) {
			model.addAttribute("selectedTask", existingTask.get());
			return "edit_task";
		}
		return "redirect:/dashboard";
		
	}
	
	
	/*
	 * Updates a task
	 */
	@RequestMapping(value="/edit-do", method=RequestMethod.POST)
	public String editTask(@ModelAttribute("selectedTask") Task editedTask, RedirectAttributes redirect, 
			@SessionAttribute("user") Useraccount loggedInUser) {
		if (editedTask.getName() == null || editedTask.getName().isEmpty()) {
			redirect.addAttribute("id", editedTask.getId());
			redirect.addFlashAttribute("taskNameError", true);
			return "redirect:/edit/{id}";
		}
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
		model.addAttribute("userEdit", user);
		return "user_edit_form";
	}
	
	
	/*
	 * Updates the user
	 */
	@RequestMapping(value="/user-edit-do", method=RequestMethod.POST)
	public String editUser(@ModelAttribute("userEdit") Useraccount userEdit, @SessionAttribute("user") Useraccount loggedInUser, 
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
	
	@RequestMapping(value="/stats", method=RequestMethod.GET)
	public String userstatsPage(RedirectAttributes redirect, @SessionAttribute("user") Useraccount loggedInUser) {
		redirect.addFlashAttribute("user", loggedInUser);
		return "redirect:/userstats";
	}
	
	
	@RequestMapping(value="/endsession", method=RequestMethod.POST)
	public String logout(SessionStatus status, @SessionAttribute("user") Useraccount loggedInUser) {
		status.setComplete();
		//TODO: Log status
		return "redirect:/login";
	}
}


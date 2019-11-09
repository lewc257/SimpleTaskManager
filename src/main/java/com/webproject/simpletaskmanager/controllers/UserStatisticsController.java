package com.webproject.simpletaskmanager.controllers;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.webproject.simpletaskmanager.entities.Task;
import com.webproject.simpletaskmanager.entities.Useraccount;

@SessionAttributes({"user"})
@Controller("/userstats")
public class UserStatisticsController {

	@RequestMapping(value="/userstats", method=RequestMethod.GET)
	public String userStatisticsPage(HttpSession session, Model model) {
		//Date joined and how long have they been a member
		//Total number of tasks
		//Completed
		//Not completed
		//percentage completed
		//How long has each task been in progress for (days, hours, minutes)
		Useraccount user = (Useraccount) model.asMap().get("user");
		if (user != null) {
			model.addAttribute("dateJoined", user.getCreated());
			model.addAttribute("noTasks", user.getTasks().size());
			model.addAttribute("noDone", countFilter(user, t -> t.getStatus()));
			model.addAttribute("noInProgress", countFilter(user, t -> !t.getStatus()));
		}
		return "user_stats";
	}
	
	private Integer countFilter(Useraccount user, Predicate<Task> pred) {
		return user.getTasks().stream().filter(pred).collect(Collectors.toList()).size();
	}
}

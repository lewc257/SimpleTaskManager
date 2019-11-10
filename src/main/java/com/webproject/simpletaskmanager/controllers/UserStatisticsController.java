package com.webproject.simpletaskmanager.controllers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;
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
import com.webproject.simpletaskmanager.extrautils.DateTimeHelpers;

import static com.webproject.simpletaskmanager.extrautils.DateTimeHelpers.*;

@SessionAttributes({"user"})
@Controller("/userstats")
public class UserStatisticsController {

	@RequestMapping(value="/userstats", method=RequestMethod.GET)
	public String userStatisticsPage(HttpSession session, Model model) {
		Useraccount user = (Useraccount) model.asMap().get("user");
		model.addAttribute("user", user);
		if (user != null) {
			Integer size = user.getTasks().size();
			model.addAttribute("noTasks", size);
			
			Integer done = countFilter(user, t -> t.getStatus());
			model.addAttribute("noDone", done);
			
			Integer inProgress = countFilter(user, t -> !t.getStatus());
			model.addAttribute("noInProgress", inProgress);
			
			Float donePerc = ((float)done * 100) / size;
			String formattedDonePerc = String.format("%.2f%%", Float.isNaN(donePerc) ? 0 : donePerc);
			model.addAttribute("donePerc", formattedDonePerc);
			
			model.addAttribute("tasks", user.getTasks());
			model.addAttribute("dateTimeHelper", new DateTimeHelpers());
			
			
		}//TODO: if the user is null, throw an exception and display it on the error page
		return "user_stats";
	}
	
	private Integer countFilter(Useraccount user, Predicate<Task> pred) {
		return user.getTasks().stream().filter(pred).collect(Collectors.toList()).size();
	}
}

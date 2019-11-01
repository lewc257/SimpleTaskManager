package com.webproject.simpletaskmanager.repositories;

import com.webproject.simpletaskmanager.entities.Task;

public interface TaskDAOLocal {
	Task findTaskById(Integer id);
	void removeTask(Task task);
	Task saveTask(Task task);
}

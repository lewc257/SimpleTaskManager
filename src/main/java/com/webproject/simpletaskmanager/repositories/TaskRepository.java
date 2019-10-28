package com.webproject.simpletaskmanager.repositories;

import com.webproject.simpletaskmanager.entities.Task;

public interface TaskRepository {
	Task findTaskById(Integer id);
	Task removeTask(Task task);
	Task saveTask(Task task);
}

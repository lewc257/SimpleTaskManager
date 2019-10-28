package com.webproject.simpletaskmanager.repositoriesimpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.webproject.simpletaskmanager.entities.Task;
import com.webproject.simpletaskmanager.repositories.TaskRepository;

@Repository
public class TaskRepositoryImpl implements TaskRepository{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Task findTaskById(Integer id) {
		return em.find(Task.class, id);
	}

	@Override
	public void removeTask(Task task) {
		throw new UnsupportedOperationException();
		
	}
	
	public void removeTaskById(Integer id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Task saveTask(Task task) {
		throw new UnsupportedOperationException();
	}

}

package com.webproject.simpletaskmanager.repositoriesdao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.webproject.simpletaskmanager.entities.Task;
import com.webproject.simpletaskmanager.repositories.TaskDAOLocal;

@Repository
public class TaskDAO implements TaskDAOLocal{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Task findTaskById(Integer id) {
		return em.find(Task.class, id);
	}

	@Override
	public Task removeTask(Task task) {
		if (em.contains(task)) {
			em.remove(task);
		} else {
			task = em.merge(task);
		}
		return task;
	}
	
	public void removeTaskById(Integer id) {
		Task task = findTaskById(id);
		if (task == null) return;
		removeTask(task);
	}

	@Override
	public Task saveTask(Task task) {
		if (task.getId() == null) {
			em.persist(task);
		} else {
			task = em.merge(task);
		}
		return task;
	}
	
	public List<Task> findAll(){
		return em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
	}

}

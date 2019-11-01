package com.webproject.simpletaskmanager.repositoriesdao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.webproject.simpletaskmanager.entities.Task;

@Repository
public class TaskDAO{

	@PersistenceContext
	private EntityManager em;
	
	public TaskDAO(EntityManager em) {
		this.em = em;
	}
	
	public Task findTaskById(Integer id) {
		return em.find(Task.class, id);
	}

	public void removeTask(Task task) {
		if (!em.contains(task)) {
			task = em.merge(task);
		}
		em.remove(task);
	}
	
	public void removeTaskById(Integer id) {
		Task task = findTaskById(id);
		if (task != null) {
			em.remove(task);
		}
	}

	public Task saveTask(Task task) {
		if (task != null && findTaskById(task.getId()) == null) {
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

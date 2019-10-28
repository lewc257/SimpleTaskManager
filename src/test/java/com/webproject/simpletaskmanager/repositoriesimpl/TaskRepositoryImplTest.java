package com.webproject.simpletaskmanager.repositoriesimpl;

import java.sql.Timestamp;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import com.webproject.simpletaskmanager.entities.Task;
import com.webproject.simpletaskmanager.entities.Useraccount;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class TaskRepositoryImplTest {
	
	@Autowired
	TaskRepositoryImpl taskRepos;
	
	@Autowired
	UseraccountRepositoryImpl useraccRepos;
	
	private static Useraccount user;
	
	@Before
	public void startUp() {
		Useraccount useraccount = new Useraccount();
		useraccount.setUsername("ABC");
		useraccount.setPassword("ABC");
		useraccount.setCreated(new Timestamp(new Date().getTime()));
		user = useraccRepos.saveAccount(useraccount);
	}
	
	@Test
	public void testAddNewTasks() {
		Timestamp created = new Timestamp(new Date().getTime());
		
		Task t1 = new Task();
		t1.setName("A");
		t1.setStatus(true);
		t1.setCreated(created);
		
		Task t2 = new Task();
		t2.setName("B");
		t2.setStatus(true);
		t2.setCreated(created);
		
		Task t3 = new Task();
		t3.setName("C");
		t3.setStatus(true);
		t3.setCreated(created);
		
		user.addTask(t1);
		user.addTask(t2);
		user.addTask(t3);
		
		Useraccount useraccount = useraccRepos.saveAccount(user);
		
		for(Task task : useraccount.getTasks()) {
			Assert.assertNotNull(task.getId());
		}
	}
	
	@Test
	public void testFindOneExistingTask() {
		Task task = taskRepos.findTaskById(1);
		Assert.assertNotNull(task);
		Assert.assertEquals(1, task.getId().intValue());
	}
	
	@Test
	public void testUpdateOneExistingTask() {
		Task task = taskRepos.findTaskById(1);
		task.setName("xyz");
		task.setStatus(false);
		task = taskRepos.saveTask(task);
		Assert.assertNotNull(task);
		Assert.assertEquals(1, task.getId().intValue());
		Assert.assertEquals("xyz", task.getName());
		Assert.assertEquals(false, task.getStatus().booleanValue());
	}
	
	@Test
	public void testDeleteOneExistingTask() {
		taskRepos.removeTaskById(1);
		Task task = taskRepos.findTaskById(1);
		Assert.assertNull(task);
	}
}

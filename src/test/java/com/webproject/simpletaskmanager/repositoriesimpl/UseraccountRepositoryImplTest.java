package com.webproject.simpletaskmanager.repositoriesimpl;

import java.sql.Timestamp;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.webproject.simpletaskmanager.repositoriesimpl.UseraccountRepositoryImpl;
import com.webproject.simpletaskmanager.entities.Useraccount;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class UseraccountRepositoryImplTest {

	@Autowired
	UseraccountRepositoryImpl useraccountRepository;
	
	@Test
	public void testFindExisitingUserById() {
		Useraccount user = useraccountRepository.findUseraccountById(1);
		Assert.assertNotNull(user);
	}
	
	@Test
	public void testFindUseraccountIdThatDoesntExist() {
		Useraccount user = useraccountRepository.findUseraccountById(999);
		Assert.assertNull(user);
	}
	
	@Test 
	public void testFindExistingUserByUsername() {
		Useraccount useraccount = useraccountRepository.findUseraccountByUsername("test");
		Assert.assertNotNull(useraccount);
	}
	
	@Test
	public void testFindNonExistingUserByUsername() {
		Useraccount useraccount = useraccountRepository.findUseraccountByUsername("xyz");
		Assert.assertNull(useraccount);
	}
	
	@Test
	public void testSaveNewUseraccount() {
		Useraccount useraccount = new Useraccount();
		useraccount.setUsername("JKL");
		useraccount.setPassword("DEF");
		Date date = new Date();
		Timestamp created = new Timestamp(date.getTime());
		useraccount.setCreated(created);
		useraccount = useraccountRepository.saveAccount(useraccount);
		Assert.assertNotNull(useraccount.getId());
	}

	
	@Test
	public void testUpdateExisitingUseraccount() {
		Useraccount existingUser = useraccountRepository.findUseraccountById(1);
		existingUser.setUsername("test1");
		existingUser.setPassword("test1");
		existingUser = useraccountRepository.saveAccount(existingUser);
		Assert.assertEquals("test1", existingUser.getUsername());
		Assert.assertEquals("test1", existingUser.getPassword());
	}
	
	@Test
	public void testDeleteExisitingUseraccount() {
		Useraccount user = useraccountRepository.findUseraccountById(1);
		useraccountRepository.deleteAccount(user);
		user = useraccountRepository.findUseraccountById(1);
		Assert.assertNull(user);
	}

}

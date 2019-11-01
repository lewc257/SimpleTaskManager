package com.webproject.simpletaskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.webproject.simpletaskmanager.entities.Useraccount;

public interface UserRepository extends JpaRepository<Useraccount, Integer> {
	
	@Query("SELECT u FROM Useraccount u WHERE u.username = :username")
	Useraccount findByUsername(@Param("username") String username);
	
	@Modifying
	@Query("DELETE FROM Useraccount u WHERE u.id = :id")
	void deleteUseraccountById(@Param("id") Integer id);
}

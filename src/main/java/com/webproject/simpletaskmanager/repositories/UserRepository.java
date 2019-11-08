package com.webproject.simpletaskmanager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webproject.simpletaskmanager.entities.Useraccount;

@Repository
public interface UserRepository extends JpaRepository<Useraccount, Integer> {
	
	@Query("SELECT u FROM Useraccount u WHERE u.username = :username")
	Useraccount findByUsername(@Param("username") String username);
	
	@Query("SELECT u FROM Useraccount u WHERE u.username = :username AND u.password = :password")
	Useraccount findUseraccount(@Param("username") String username, @Param("password") String password);
	
	@Modifying(flushAutomatically=true)
	@Query("DELETE FROM Useraccount u WHERE u.id = :id")
	void deleteUseraccountById(@Param("id") Integer id);
}

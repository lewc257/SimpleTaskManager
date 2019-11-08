package com.webproject.simpletaskmanager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webproject.simpletaskmanager.entities.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
	
	@Query("SELECT ui FROM UserInfo ui WHERE ui.personalEmail = :personalEmail")
	UserInfo findByEmail(@Param("personalEmail") String personalEmail);
}

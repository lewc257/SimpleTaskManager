package com.webproject.simpletaskmanager.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.webproject.simpletaskmanager.entities.Useraccount;

public interface UseraccountRepository {
	Useraccount findUseraccountById(Integer id);
	Useraccount findUseraccountByUsername(String username);
	Useraccount saveAccount(Useraccount useraccount);
	void deleteAccount(Useraccount useraccount);
}

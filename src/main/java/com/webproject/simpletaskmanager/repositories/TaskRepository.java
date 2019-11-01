package com.webproject.simpletaskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webproject.simpletaskmanager.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
	@Query("SELECT t FROM Task t WHERE t.id = :id")
	Task findTaskById(@Param("id")Integer id);
	
	@Modifying(flushAutomatically=true)
	@Query("DELETE FROM Task t WHERE t.id = :id")
	void deleteTaskById(@Param("id")Integer id);
	
	@Modifying(flushAutomatically=true)
	@Query("UPDATE Task t SET t.status = :status WHERE t.id = :id")
	void updateStatusById(@Param("status")Boolean status, @Param("id")Integer id);
	
}

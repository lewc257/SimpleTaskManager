package com.webproject.simpletaskmanager.entities;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.persistence.*;

import com.webproject.simpletaskmanager.extrautils.DateTimeHelpers;

@Entity
@Table(name = "task")
public class Task{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "status")
	private Boolean status;
	// created
	@Column(name = "created")
	private Timestamp created;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Useraccount useraccount;

	public Useraccount getUseraccount() {
		return useraccount;
	}

	public void setUseraccount(Useraccount useraccount) {
		this.useraccount = useraccount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public boolean replaceWith(Task newTask) {
		if (newTask == null)
			return false;
		this.setId(newTask.getId());
		this.setName(newTask.getName());
		this.setStatus(newTask.getStatus());
		this.setCreated(newTask.getCreated());
		this.setUseraccount(newTask.getUseraccount());
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Task))
			return false;

		Task task = (Task) obj;

		if (id != null ? !id.equals(task.getId()) : task.getId() != null)
			return false;
		if (name != null ? !name.equals(task.name) : task.getName() != null)
			return false;
		if (status != null ? status != task.status : task.status != null)
			return false;
		if (created != null ? !created.equals(task.getCreated()) : task.created != null)
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", status=" + status + ", created=" + created + "]";
	}

}

package com.webproject.simpletaskmanager.entities;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="task")
public class Task {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="task_id")
	private Integer id;
	//name
	@Column(name="name")
	private String name;
	//status
	@Column(name="status")
	private Boolean status;
	//created
	@Column(name="created")
	private Timestamp created;
	
	@ManyToOne
	@JoinColumn(name="user_id")
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
	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", status=" + status + ", created=" + created + "]";
	}
	
}

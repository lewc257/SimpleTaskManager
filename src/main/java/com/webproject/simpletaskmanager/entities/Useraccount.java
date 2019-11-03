package com.webproject.simpletaskmanager.entities;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "user_account")
public class Useraccount{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Integer id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="created")
	private Timestamp created;
	
	@OneToOne(mappedBy="useraccount", cascade=CascadeType.ALL)
	private UserInfo userInfo;
	
	@OneToMany(mappedBy="useraccount", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Task> tasks = new ArrayList<Task>();
	
	public void addTask(Task task) {
		if (tasks == null) {
			tasks = new ArrayList<Task>();
		}
		tasks.add(task);
		task.setUseraccount(this);
	}
	
	public boolean removeTask(Task task) {
		if (tasks == null) {
			tasks = new ArrayList<Task>();
		}
		return tasks.remove(task);
	}
	
	public void mergeTask(Task task) {
		if (task != null) {
			for (int i = 0; i < tasks.size(); i++) {
				if (tasks.get(i).getId() == task.getId()) {
					tasks.get(i).setName(task.getName());
					tasks.get(i).setStatus(task.getStatus());
					tasks.get(i).setCreated(task.getCreated());
					tasks.get(i).setUseraccount(task.getUseraccount());
					return;
				}
			}
		}
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
		userInfo.setUseraccount(this);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	
	public Timestamp getCreated() {
		return created;
	}
	
	@Override
	public String toString() {
		return "Useraccount [id=" + id + ", username=" + username + ", password=" + password + ", created=" + created
				+ "]";
	}
}

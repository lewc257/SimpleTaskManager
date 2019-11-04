package com.webproject.simpletaskmanager.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginForm {
	
	private String username;
	
	private String password;
	
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
	@Override
	public String toString() {
		return "LoginForm [username=" + username + ", password=" + password + "]";
	}
}

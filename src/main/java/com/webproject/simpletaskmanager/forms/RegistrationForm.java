package com.webproject.simpletaskmanager.forms;

public class RegistrationForm {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String personalEmail;
	
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPersonalEmail() {
		return personalEmail;
	}
	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}
	@Override
	public String toString() {
		return "RegistrationForm [username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", personalEmail=" + personalEmail + "]";
	}
	
}

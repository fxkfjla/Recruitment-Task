package com.example.recruitmenttask.Models;

public class UserRequest
{
	public UserRequest() {}
	
	public UserRequest(String name, String surname, String login)
	{
		this.name = name;
		this.surname = surname;
		this.login = login;
	}
	
	// Getters, setters
	public String getName() { return name; }
	public String getSurname() { return surname; }
	public String getLogin() { return login; }
	public void setName(String name) { this.name = name; }
	public void setSurname(String surname) { this.surname = surname; }
	public void setLogin(String login) { this.login = login; }
	
	private String name;
	private String surname;
	private String login;
}
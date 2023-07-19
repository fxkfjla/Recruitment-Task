package com.example.recruitmenttask.Models;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "users")
public class UserList
{
	public UserList() {}
	
	public UserList(List<User> users)
	{
		this.userList = users;
	}
	
	// Getters, setters
	public List<User> getUsers() { return userList; }
	public void setUsers(List<User> users) { this.userList = users; }
	
	@XmlElement(name = "user")
	private List<User> userList;
}
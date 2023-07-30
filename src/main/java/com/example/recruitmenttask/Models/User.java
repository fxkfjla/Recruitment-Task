package com.example.recruitmenttask.Models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User
{
	public User() {}
	
	public User(String name, String surname, String login)
	{
		this.name = name;
		this.surname = surname;
		this.login = login;
	}
	
	// Getters, setters
	public Long getId() { return id; }
	public String getName() { return name; }
	public String getSurname() { return surname; }
	public String getLogin() { return login; }
	public void setId(Long id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setSurname(String surname) { this.surname = surname; }
	public void setLogin(String login) { this.login = login; }
	
	@Override
    public boolean equals(Object o)
	{
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        
        User rhs = (User)o;
        
        return getName().equals(rhs.getName()) &&
        		getSurname().equals(rhs.getSurname()) &&
        		getLogin().equals(rhs.getLogin());
    }
		
    @Override
    public int hashCode()
    {
        return Objects.hash(getName(), getSurname(), getLogin());
    }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String surname;
	private String login;
}

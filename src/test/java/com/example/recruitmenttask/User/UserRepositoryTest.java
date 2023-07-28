package com.example.recruitmenttask.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.recruitmenttask.Models.User;
import com.example.recruitmenttask.Repositories.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRepositoryTest
{
	@BeforeEach
	public void beforeEach()
	{
		List<User> users = new ArrayList<>();
		users.add(new User("Bjarne", "Stroustrup", "strup"));
		users.add(new User("Ryan", "Gosling", "idrive"));
		users.add(new User("James", "Gosling", "idontdrive"));
		
		sut.saveAll(users);
	}
		
	@AfterEach
	public void afterEach()
	{
		sut.deleteAll();
	}
	
	@Test
	public void Truncates_table()
	{
		// When
		sut.truncateTable();
		
		// Then
		List<User> expected = sut.findAll();
		assertThat(expected).isEmpty();
	}
	
	@Test
	public void Finds_users_by_name_when_exist()
	{
		// Given
		String name = "Bjarne";
		
		// When
		List<User> users = sut.findByNameContainingOrSurnameContainingOrLoginContaining(name, null, null, any()).getContent();
		
		// Then
		assertThat(users).allMatch(user -> user.getName().equals(name));
	}
	
	@Test
	public void Finds_users_by_surname_when_exist()
	{
		// Given
		String surname = "Gosling";
		
		// When
		List<User> users = sut.findByNameContainingOrSurnameContainingOrLoginContaining(null, surname, null, any()).getContent();
		
		// Then
		assertThat(users).allMatch(user -> user.getSurname().equals(surname));		
	}
	
	@Test
	public void Finds_users_by_login_when_exist()
	{
		// Given
		String login = "idontdrive";
		
		// When
		List<User> users = sut.findByNameContainingOrSurnameContainingOrLoginContaining(null, null, login, any()).getContent();
		
		// Then
		assertThat(users).allMatch(user -> user.getLogin().equals(login));		
	}
	
	@Test
	public void Does_not_find_users_by_name_when_do_not_exist()
	{
		// Given
		String name = "Mariusz";
		
		// When
		List<User> users = sut.findByNameContainingOrSurnameContainingOrLoginContaining(name, null, null, any()).getContent();
		
		// Then
		assertThat(users).isEmpty();
	}
	
	@Test
	public void Does_not_find_users_by_surname_when_do_not_exist()
	{
		// Given
		String surname = "Pudzianowski";
		
		// When
		List<User> users = sut.findByNameContainingOrSurnameContainingOrLoginContaining(null, surname, null, any()).getContent();
		
		// Then
		assertThat(users).isEmpty();
	}
	
	@Test
	public void Does_not_find_users_by_login_when_do_not_exist()
	{
		// Given
		String login = "pudzian";
		
		// When
		List<User> users = sut.findByNameContainingOrSurnameContainingOrLoginContaining(null, null, login, any()).getContent();
		
		// Then
		assertThat(users).isEmpty();
	}
	
	@Autowired
	private UserRepository sut;
}

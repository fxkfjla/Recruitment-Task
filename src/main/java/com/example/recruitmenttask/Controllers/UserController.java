package com.example.recruitmenttask.Controllers;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.recruitmenttask.Models.User;
import com.example.recruitmenttask.Services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController
{
	public UserController(UserService userService)
	{
		this.userService = userService;
	}
	
	@PostMapping("/load-xml")
	public ResponseEntity<String> uploadXMLFile(@RequestParam("file") MultipartFile file) throws JAXBException, IOException
	{
		return userService.uploadXMLFile(file);
	}

	@GetMapping
	public ResponseEntity<List<User>> findAll
	(
		@RequestParam(defaultValue = "0") int page, 
		@RequestParam(defaultValue = "13") int size,
		@RequestParam(defaultValue = "ASC") String direction,
		@RequestParam(defaultValue = "id") String field
	)
	{
	    Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		//TODO: handle page index out of bound
		return userService.findAll(PageRequest.of(page, size, Sort.by(sortDirection, field)));
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<User>> findByNameOrSurnameOrLogin
	(
		@RequestParam String searchField,
		@RequestParam(defaultValue = "0") int page, 
		@RequestParam(defaultValue = "13") int size,
		@RequestParam(defaultValue = "ASC") String direction,
		@RequestParam(defaultValue = "id") String field
	)
	{
		Sort.Direction sortDirection = Sort.Direction.fromString(direction);
		//TODO: handle page index out of bound
		return userService.findByNameOrSurnameOrLogin(searchField, PageRequest.of(page, size, Sort.by(sortDirection, field)));
	}
	
	private final UserService userService;
}
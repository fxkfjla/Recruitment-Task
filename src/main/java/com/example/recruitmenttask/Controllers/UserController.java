package com.example.recruitmenttask.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.recruitmenttask.Exceptions.InvalidXMLDataException;
import com.example.recruitmenttask.Models.User;
import com.example.recruitmenttask.Models.DTO.PageRequestDTO;
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
	public ResponseEntity<String> uploadXMLFile(@RequestPart MultipartFile file) throws InvalidXMLDataException
	{
		return userService.uploadXMLFile(file);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> findAll(@Valid PageRequestDTO page)
	{
		return userService.findAll(page);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<User>> findByNameOrSurnameOrLogin(String search, @Valid PageRequestDTO page)
	{
		return userService.findByNameOrSurnameOrLogin(search, page);
	}
	
	private final UserService userService;
}
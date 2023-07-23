package com.example.recruitmenttask.Controllers;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	private final UserService userService;
}
package com.example.recruitmenttask.Controllers;

import javax.xml.bind.JAXBException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.recruitmenttask.Services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController
{
	public UserController(UserService userService)
	{
		this.userService = userService;
	}
	
	@PostMapping(value = "/load-xml", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
	public ResponseEntity<String> loadXMLData(@RequestBody String XMLData) throws JAXBException
	{
		return userService.loadXMLData(XMLData);
	}

	private final UserService userService;
}
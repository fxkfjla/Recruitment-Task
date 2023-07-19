package com.example.recruitmenttask.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.recruitmenttask.Models.User;
import com.example.recruitmenttask.Repositories.UserRepository;
import com.example.recruitmenttask.Utils.XMLDataHandler;

@Service
public class UserService
{
	public UserService(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}
	
	public ResponseEntity<String> loadXMLData(String XMLData)
	{
		try
		{
			List<User> users = XMLDataHandler.convertXMLToList(XMLData);
			userRepository.saveAll(users);
			
			return ResponseEntity.ok("Success");
		}
		catch(Exception e)
		{
			System.out.println("Failed to load users from xml.");
			e.printStackTrace();
			// Handle JAXB, marshal, illegalArgument exceptions
			// TODO: throw bad file format exception
			return ResponseEntity.ok("Failure");
		}
	}
	
	private final UserRepository userRepository;
}

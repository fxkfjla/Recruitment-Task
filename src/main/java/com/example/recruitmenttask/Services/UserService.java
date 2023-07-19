package com.example.recruitmenttask.Services;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.recruitmenttask.Models.User;
import com.example.recruitmenttask.Models.UserList;
import com.example.recruitmenttask.Repositories.UserRepository;

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
			JAXBContext context = JAXBContext.newInstance(UserList.class, User.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			// Pass XMLData to stream
			StringReader reader = new StringReader(XMLData);
			// Convert XML to java object from stream
			UserList userList = (UserList)unmarshaller.unmarshal(reader);
			
			userRepository.saveAll(userList.getUsers());
			
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

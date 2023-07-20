package com.example.recruitmenttask.Services;

import java.util.List;

import javax.xml.bind.JAXBException;

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
	
	public ResponseEntity<String> loadXMLData(String XMLData) throws JAXBException
	{
			List<User> users = XMLDataHandler.convertXMLToList(XMLData);
			userRepository.saveAll(users);
			
			return ResponseEntity.ok("Success");
	}
	
	private final UserRepository userRepository;
}

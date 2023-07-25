package com.example.recruitmenttask.Services;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	
	public ResponseEntity<String> uploadXMLFile(MultipartFile file) throws JAXBException, IOException
	{
			List<User> users = XMLDataHandler.convertXMLToList(file);
			
			// Clear data in table
			userRepository.truncateTable();
			userRepository.saveAll(users);
			
			return ResponseEntity.ok("Success");
	}
	
	public ResponseEntity<List<User>> findAll(Pageable pageable)
	{
		Page<User> usersPage = userRepository.findAll(pageable);
	    List<User> users = usersPage.getContent();
	    
		HttpHeaders responseHeaders = getXTotalCountHeader(usersPage.getTotalElements());
	 	    
		return ResponseEntity.ok().headers(responseHeaders).body(users);
	}
	
	public ResponseEntity<List<User>> findByNameOrSurnameOrLogin(String searchField, Pageable pageable)
	{
		Page<User> usersPage = userRepository.findByNameContainingOrSurnameContainingOrLoginContaining
				(searchField, searchField, searchField, pageable);
	    List<User> users = usersPage.getContent();
	    
		HttpHeaders responseHeaders = getXTotalCountHeader(usersPage.getTotalElements());
		
		return ResponseEntity.ok().headers(responseHeaders).body(users);
	}
	
	private HttpHeaders getXTotalCountHeader(long totalElements)
	{
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.add("X-Total-Count", String.valueOf(totalElements));
	    
	    return responseHeaders;
	}
	
	private final UserRepository userRepository;
}

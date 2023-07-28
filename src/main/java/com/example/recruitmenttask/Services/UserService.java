package com.example.recruitmenttask.Services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.recruitmenttask.Exceptions.InvalidXMLDataException;
import com.example.recruitmenttask.Models.User;
import com.example.recruitmenttask.Models.DTO.PageRequestDTO;
import com.example.recruitmenttask.Repositories.UserRepository;
import com.example.recruitmenttask.Utils.PageRequestDTOToPageRequestConverter;
import com.example.recruitmenttask.Utils.XMLDataHandler;

@Service
public class UserService
{
	public UserService(UserRepository userRepository, PageRequestDTOToPageRequestConverter converter)
	{
		this.userRepository = userRepository;
		this.converter = converter;
	}
	
	public ResponseEntity<String> uploadXMLFile(MultipartFile file) throws InvalidXMLDataException
	{
		if(file.getContentType().equals(MediaType.APPLICATION_XML_VALUE))
		{
			List<User> users = XMLDataHandler.convertXMLToList(file);
		
			// Clear data in table
			userRepository.truncateTable();
			userRepository.saveAll(users);
		
			return ResponseEntity.ok("File successfully uploaded!");
		}
		else
		{
			return new ResponseEntity<>("Upload only XML files!", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		}
	}
	
	public ResponseEntity<List<User>> findAll(PageRequestDTO pageRequest)
	{
		PageRequest page = converter.convert(pageRequest);
		Page<User> usersPage = userRepository.findAll(page);
		
		HttpHeaders responseHeaders = getXTotalCountHeader(usersPage.getTotalElements());
	 	    
		return ResponseEntity.ok().headers(responseHeaders).body(usersPage.getContent());
	}
	
	public ResponseEntity<List<User>> findByNameOrSurnameOrLogin(String search, PageRequestDTO pageRequest)
	{
		PageRequest page = converter.convert(pageRequest);
		Page<User> usersPage = userRepository.findByNameContainingOrSurnameContainingOrLoginContaining(search, search, search, page);
		
		HttpHeaders responseHeaders = getXTotalCountHeader(usersPage.getTotalElements());
	 	    
		return ResponseEntity.ok().headers(responseHeaders).body(usersPage.getContent());
	}
	
	private HttpHeaders getXTotalCountHeader(long totalElements)
	{
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.add("X-Total-Count", String.valueOf(totalElements));
	    
	    return responseHeaders;
	}
	
	private final UserRepository userRepository;
	private final PageRequestDTOToPageRequestConverter converter;
}

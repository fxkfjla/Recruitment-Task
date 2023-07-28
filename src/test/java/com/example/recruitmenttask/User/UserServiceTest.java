package com.example.recruitmenttask.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.data.domain.Page;

import com.example.recruitmenttask.Exceptions.InvalidXMLDataException;
import com.example.recruitmenttask.Models.DTO.PageRequestDTO;
import com.example.recruitmenttask.Repositories.UserRepository;
import com.example.recruitmenttask.Services.UserService;
import com.example.recruitmenttask.Utils.PageRequestDTOToPageRequestConverter;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest
{		
	@Test
	public void Uploads_XML_data_with_valid_format() throws InvalidXMLDataException
	{
		// Given
		String contents = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><users><user></user></users>";
		MockMultipartFile xmlFile = new MockMultipartFile
		(
			"file", 
			"file.xml", 
			MediaType.TEXT_XML_VALUE, 
			contents.getBytes()
		);

        // When
        ResponseEntity<String> response = sut.uploadXMLFile(xmlFile);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File successfully uploaded!", response.getBody());
        verify(userRepository).truncateTable();
        verify(userRepository).saveAll(anyList());
	}
	
	@Test
	public void Does_not_upload_XML_data_with_invalid_format() throws InvalidXMLDataException
	{
		// Given
		String contents = "plain text";
		MockMultipartFile xmlFile = new MockMultipartFile
		(
			"file", 
			"file.xml", 
			MediaType.TEXT_PLAIN_VALUE, 
			contents.getBytes()
		);

		// When
        ResponseEntity<String> response = sut.uploadXMLFile(xmlFile);

        // Then
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
        assertEquals("Upload only XML files!", response.getBody());
        verify(userRepository, times(0)).truncateTable();
        verify(userRepository, times(0)).saveAll(anyList());
	}
	
	@Test
	public void Finds_all_users()
	{
		// Given
		PageRequestDTO pageRequestDTO = new PageRequestDTO(1, 13, "asc", "id");
		PageRequest pageRequest = PageRequest.of(1, 13, Sort.by(Sort.Direction.ASC, "id"));
		
		// When
		when(converter.convert(pageRequestDTO)).thenReturn(pageRequest);
		when(userRepository.findAll(pageRequest)).thenReturn(Page.empty());
		sut.findAll(pageRequestDTO);
		
		// Then
		verify(userRepository).findAll(pageRequest);
	}
	
	@Test
	public void Finds_Users_by_name_or_surname_or_login()
	{
		// Given
		PageRequestDTO pageRequestDTO = new PageRequestDTO(1, 13, "asc", "id");
		PageRequest pageRequest = PageRequest.of(1, 13, Sort.by(Sort.Direction.ASC, "id"));
		String search = "";
		
		// When
		when(converter.convert(pageRequestDTO)).thenReturn(pageRequest);
		when(userRepository.findByNameContainingOrSurnameContainingOrLoginContaining(search, search, search, pageRequest))
		.thenReturn(Page.empty());
		sut.findByNameOrSurnameOrLogin(search, pageRequestDTO);
		
		// Then
		verify(userRepository).findByNameContainingOrSurnameContainingOrLoginContaining(search, search, search, pageRequest);
	}
	
	@Mock
	private UserRepository userRepository;
	@Mock
	private PageRequestDTOToPageRequestConverter converter;
	@InjectMocks
	private UserService sut;
}

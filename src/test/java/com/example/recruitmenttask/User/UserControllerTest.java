package com.example.recruitmenttask.User;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.example.recruitmenttask.Controllers.UserController;
import com.example.recruitmenttask.Models.User;
import com.example.recruitmenttask.Models.DTO.PageRequestDTO;
import com.example.recruitmenttask.Services.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest
{
	@Test
	public void Uploads_XML_file_when_valid_xml_format() throws Exception
	{
		// Given
		String response = "Success";
		String contents = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		MockMultipartFile xmlFile = new MockMultipartFile
		(
			"file", 
			"file.xml", 
			MediaType.APPLICATION_XML_VALUE, 
			contents.getBytes()
		);

		// When
		when(userService.uploadXMLFile(any(MultipartFile.class))).thenReturn(ResponseEntity.ok(response));
		
		// Then
		mockMvc.perform(multipart("/api/users/load-xml")
		.file(xmlFile))
		.andExpect(status().isOk())
		.andExpect(content().string(response));
	}
	
	@Test
	public void Does_not_upload_XML_file_with_invalid_format() throws Exception
	{
		// Given
		String responseString = "Failure";
		ResponseEntity<String> response = new ResponseEntity<>(responseString, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		String contents = "plain text";
		MockMultipartFile txtFile = new MockMultipartFile
		(
			"file", 
			"file.txt", 
			MediaType.TEXT_PLAIN_VALUE, 
			contents.getBytes()
		);
		
		// When
		when(userService.uploadXMLFile(any(MultipartFile.class))).thenReturn(response);
		
		// Then
		mockMvc.perform(multipart("/api/users/load-xml")
		.file(txtFile))
		.andExpect(status().isUnsupportedMediaType())
		.andExpect(content().string(responseString));
	}
	
    @Test
    public void Finds_all_users_with_valid_params() throws Exception
    {
    	// Given
    	PageRequestDTO page = new PageRequestDTO(0, 13, "asc", "id");
    	
        List<User> users = new ArrayList<>();
    	users.add(new User("Will","Will", "Smith"));
        users.add(new User("Smith","Will", "Smith"));
        
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", String.valueOf(page.getPage()));
		params.add("size", String.valueOf(page.getSize()));
		params.add("direction", page.getDirection());
		params.add("field", page.getField());
        
		// When
        when(userService.findAll(any(PageRequestDTO.class))).thenReturn(ResponseEntity.ok(users));
    	
        // Then
        mockMvc.perform(get("/api/users")
		.params(params))
	    .andExpect(status().isOk())
	    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	    .andExpect(jsonPath("$[0].name").value("Will"))
	    .andExpect(jsonPath("$[0].surname").value("Will"))
	    .andExpect(jsonPath("$[0].login").value("Smith"))
	    .andExpect(jsonPath("$[1].name").value("Smith"))
        .andExpect(jsonPath("$[1].surname").value("Will"))
	    .andExpect(jsonPath("$[1].login").value("Smith"));
    }
    
    @Test
    public void Finds_users_by_name_or_surname_or_login_with_valid_params() throws Exception
    {
    	// Given
    	PageRequestDTO page = new PageRequestDTO(0, 13, "asc", "id");
    	
        List<User> users = new ArrayList<>();
    	users.add(new User("Yes","Will", "Smith"));
        users.add(new User("Will","Smith", "Will"));
        
    	String search = "Smith";
        
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("search", search);
        params.add("page", String.valueOf(page.getPage()));
		params.add("size", String.valueOf(page.getSize()));
		params.add("direction", page.getDirection());
		params.add("field", page.getField());
        
		// When
        when(userService.findByNameOrSurnameOrLogin(any(String.class), any(PageRequestDTO.class)))
        .thenReturn(ResponseEntity.ok(users));
    	
        // Then
        mockMvc.perform(get("/api/users/search")
		.params(params))
	    .andExpect(status().isOk())
	    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	    .andExpect(jsonPath("$[0].name").value("Yes"))
	    .andExpect(jsonPath("$[0].surname").value("Will"))
	    .andExpect(jsonPath("$[0].login").value("Smith"))
	    .andExpect(jsonPath("$[1].name").value("Will"))
        .andExpect(jsonPath("$[1].surname").value("Smith"))
	    .andExpect(jsonPath("$[1].login").value("Will"));
    }
    
    @Test
    public void Finds_all_users_with_invalid_params() throws Exception
    {
    	// Given
    	PageRequestDTO page = new PageRequestDTO(-1, 0, "acs", "di");
    	 
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", String.valueOf(page.getPage()));
		params.add("size", String.valueOf(page.getSize()));
		params.add("direction", page.getDirection());
		params.add("field", page.getField());
    	
        // Then
        mockMvc.perform(get("/api/users")
		.params(params))
	    .andExpect(status().isBadRequest());
    }
    
    @Test
    public void Finds_users_by_name_or_surname_or_login_with_invalid_params() throws Exception
    {
    	// Given
    	PageRequestDTO page = new PageRequestDTO(-4, -5, "dsec", "anme");
    	
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("search", "unused");
        params.add("page", String.valueOf(page.getPage()));
		params.add("size", String.valueOf(page.getSize()));
		params.add("direction", page.getDirection());
		params.add("field", page.getField());
    	
        // Then
        mockMvc.perform(get("/api/users/search")
		.params(params))
	    .andExpect(status().isBadRequest());
    }
    
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
}
package com.example.recruitmenttask.Utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.example.recruitmenttask.Exceptions.InvalidXMLDataException;
import com.example.recruitmenttask.Models.User;

public class XMLDataHandlerTest
{
	@Test
	public void Converts_XML_to_list_when_data_is_valid() throws JAXBException, InvalidXMLDataException
	{
		// Given
		User user = new User("name1", "surname1", "login1");
		String contents = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
						+ "<users><user><name>" + user.getName() + "</name>"
						+ "<surname>" + user.getSurname() + "</surname>"
						+ "<login>" + user.getLogin() + "</login></user></users>";
		
		MockMultipartFile xmlFile = new MockMultipartFile
		(
			"file", 
			"file.xml", 
			MediaType.TEXT_XML_VALUE, 
			contents.getBytes()
		);
		
		// When
		List<User> users = XMLDataHandler.convertXMLToList(xmlFile);
		
		// Then
		assertThat(users).allMatch(u -> u.equals(user));
	}
	
	@Test
	public void Does_not_convert_XML_to_list_when_data_is_not_valid() 
	{
		// Given
		User user = new User("name1", "surname1", "login1");
		String contents = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<user><user><name>" + user.getName() + "</name>"
			+ "<surname>" + user.getSurname() + "</surname>"
			+ "<login>" + user.getLogin() + "</login></user></users>";
		
		MockMultipartFile xmlFile = new MockMultipartFile
		(
			"file", 
			"file.xml", 
			MediaType.TEXT_XML_VALUE, 
			contents.getBytes()
		);
		
		// When
	    Exception exception = assertThrows(InvalidXMLDataException.class, () -> 
	    {
	    	XMLDataHandler.convertXMLToList(xmlFile);
	    });
		
		// Then
	    String expectedMessage = "Invalid data format!";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void Generates_users_to_XML() throws IOException
	{
		// Given
		int amount = 2;
		String fileName = "test.xml";
		boolean formatOutput = false;
		String expectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
			+ "<users><user><login>login1</login>"
			+ "<name>name1</name>"
			+ "<surname>surname1</surname></user>"
			+ "<user><login>login2</login>"
			+ "<name>name2</name>"
			+ "<surname>surname2</surname></user></users>";
			
		// When
		XMLDataHandler.generateUsersToXML(amount, fileName, formatOutput);
		
		// Then
	    File file = new File(fileName);
	    assertTrue(file.exists());
	    
        String data = FileUtils.readFileToString(file, "UTF-8");

        assertThat(data).isEqualTo(expectedResult);
	    file.delete();
    }
}

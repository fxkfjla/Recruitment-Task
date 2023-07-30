package com.example.recruitmenttask.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;

import org.springframework.web.multipart.MultipartFile;

import com.example.recruitmenttask.Exceptions.InvalidXMLDataException;
import com.example.recruitmenttask.Exceptions.JAXBInitializationRuntimeException;
import com.example.recruitmenttask.Models.User;
import com.example.recruitmenttask.Models.DTO.UserList;

public class XMLDataHandler
{
	public static List<User> convertXMLToList(MultipartFile file) throws InvalidXMLDataException
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(UserList.class, User.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			return unmarshalFile(file, unmarshaller);
		}
		catch(UnmarshalException e)
		{
			throw new InvalidXMLDataException("Invalid data format!");
		}
		catch(JAXBException e)
		{
			throw new JAXBInitializationRuntimeException("Can't instantiate JAXBContext or Unmarshaller!");
		}
	}
	
	public static void generateUsersToXML(int amount, String fileName, boolean formatOutput)
	{
		UserList userList = new UserList(generateUsersToList(amount));

		try
		{
			JAXBContext context = JAXBContext.newInstance(UserList.class, User.class);
			Marshaller marshaller = context.createMarshaller();
			
			// Format output
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatOutput);
	        // Convert to XML with output to users.xml
			marshaller.marshal(userList, new File(fileName));
		}
		catch(JAXBException e)
		{
			throw new JAXBInitializationRuntimeException("Can't instantiate JAXBContext or Marshaller!");
		}
	}
	
	private static List<User> generateUsersToList(int amount)
	{
		List<User> users = new ArrayList<>();
		
		for(int i = 1; i <= amount; i++)
			users.add(new User("name" + i, "surname" + i, "login" + i));
		
		return users;
	}
	
	private static List<User> unmarshalFile(MultipartFile file, Unmarshaller unmarshaller) throws JAXBException
	{
		try(InputStream inputStream = file.getInputStream())
		{
			UserList userList = (UserList)unmarshaller.unmarshal(inputStream);
			
			return userList.getUsers();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			
			return Collections.emptyList();
		}
	}
}
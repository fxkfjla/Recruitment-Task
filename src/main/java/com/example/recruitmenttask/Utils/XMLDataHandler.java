package com.example.recruitmenttask.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.web.multipart.MultipartFile;

import com.example.recruitmenttask.Models.User;
import com.example.recruitmenttask.Models.UserList;

public class XMLDataHandler
{
	public static List<User> convertXMLToList(MultipartFile file) throws JAXBException, IOException
	{
		JAXBContext context = JAXBContext.newInstance(UserList.class, User.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		InputStream inputStream = file.getInputStream();
		UserList userList = (UserList)unmarshaller.unmarshal(inputStream);
		inputStream.close();
		
		return userList.getUsers();
	}
	
	public static void generateUsersToXML(int amount)
	{
		UserList userList = new UserList(generateUsersToList(amount));

		try
		{
			JAXBContext context = JAXBContext.newInstance(UserList.class, User.class);
			Marshaller marshaller = context.createMarshaller();
			
			// Format output
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        // Convert to XML with output to users.xml
			marshaller.marshal(userList, new File("users.xml"));
		}
		catch(JAXBException e)
		{
			System.out.println("Failed generating xml users. Reason:");
			e.printStackTrace();
		}
	}
	
	private static List<User> generateUsersToList(int amount)
	{
		List<User> users = new ArrayList<>();
		
		for(int i = 1; i <= amount; i++)
			users.add(new User("name" + i, "surname" + i, "login" + i));
		
		return users;
	}
}
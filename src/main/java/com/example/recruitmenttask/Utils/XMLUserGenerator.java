package com.example.recruitmenttask.Utils;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.example.recruitmenttask.Models.User;

public class XMLUserGenerator
{
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
		catch(Exception e)
		{
			System.out.println("Failed to generate users to xml file.");
			e.printStackTrace();
			// Handle JABX, marshal, illegalArgument exceptions
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
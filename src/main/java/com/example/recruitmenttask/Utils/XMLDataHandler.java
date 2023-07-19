package com.example.recruitmenttask.Utils;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.example.recruitmenttask.Models.User;
import com.example.recruitmenttask.Models.UserList;

public class XMLDataHandler
{
	public static List<User> convertXMLToList(String XMLData) throws Exception
	{
		JAXBContext context = JAXBContext.newInstance(UserList.class, User.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		// Pass XMLData to stream
		StringReader reader = new StringReader(XMLData);
		// Convert XML to java object from stream
		UserList userList = (UserList)unmarshaller.unmarshal(reader);
		
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
		catch(Exception e)
		{
			System.out.println("Failed to generate users to xml file.");
			e.printStackTrace();
			// TODO: Handle JABX, marshal, illegalArgument exceptions outside this function
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
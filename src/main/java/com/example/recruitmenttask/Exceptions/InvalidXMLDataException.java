package com.example.recruitmenttask.Exceptions;

@SuppressWarnings("serial")
public class InvalidXMLDataException extends Exception
{
	public InvalidXMLDataException(String message)
	{
		super(message);
	}
	
	public InvalidXMLDataException(Throwable cause)
	{
		super(cause);
	}
	
	public InvalidXMLDataException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
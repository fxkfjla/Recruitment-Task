package com.example.recruitmenttask.Exceptions;

@SuppressWarnings("serial")
public class JAXBInitializationRuntimeException extends RuntimeException
{
	public JAXBInitializationRuntimeException(String message)
	{
		super(message);
	}
	
	public JAXBInitializationRuntimeException(Throwable cause)
	{
		super(cause);
	}
	
	public JAXBInitializationRuntimeException(String message, Throwable cause)
	{
		super(message, cause);
	}
}

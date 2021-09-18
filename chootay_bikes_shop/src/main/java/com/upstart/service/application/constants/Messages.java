package com.upstart.service.application.constants;

import java.io.IOException;

import uk.co.planetbeyond.util.CustomResourceBundle;

public class Messages extends CustomResourceBundle
{
	private static Messages message = null;
	
	private Messages() throws IOException
	{
		super("message");

	}

	/**
	 * provides a single instance of class
	 * 
	 * @return the new instance of class if null, otherwise return newly created instance
	 * @throws IOException
	 */
	public static synchronized Messages get() throws IOException
	{
		if (message == null)
		{
			message = new Messages();
		}

		return message;
	}
	
	// Messages against CRUD operations
	public String RECORD_INSERTED_SUCCESSFUL_MESSAGE = getString("RECORD_INSERTED_SUCCESSFUL_MESSAGE");
	public String RECORD_INSERTED_FAILED_MESSAGE = getString("RECORD_INSERTED_FAILED_MESSAGE");
	public String RECORD_FOUND_SUCCESSFUL_MESSAGE = getString("RECORD_FOUND_SUCCESSFUL_MESSAGE");
	public String RECORD_SINGLE_FOUND_FAILED_MESSAGE = getString("RECORD_SINGLE_FOUND_FAILED_MESSAGE");
	public String RECORD_LIST_FOUND_FAILED_MESSAGE = getString("RECORD_LIST_FOUND_FAILED_MESSAGE");
	public String RECORD_UPDATE_SUCCESSFUL_MESSAGE = getString("RECORD_UPDATE_SUCCESSFUL_MESSAGE");
	public String RECORD_UPDATE_FAILED_MESSAGE = getString("RECORD_UPDATE_FAILED_MESSAGE");
	public String RECORD_DELETE_SUCCESSFUL_MESSAGE = getString("RECORD_DELETE_SUCCESSFUL_MESSAGE");
	public String RECORD_DELETE_FAILED_MESSAGE = getString("RECORD_DELETE_FAILED_MESSAGE");
	public String RECORD_NOT_FOUND = getString("RECORD_NOT_FOUND");

	public String LOGIN_AUTHENTICATION_SUCCESSFULL = getString("LOGIN_AUTHENTICATION_SUCCESSFULL");
	public String LOGIN_AUTHENTICATION_FAILED = getString("LOGIN_AUTHENTICATION_FAILED");

	public String SERVICE_EXCEPTION_MESSAGE = getString("SERVICE_EXCEPTION_MESSAGE");
	public String INVALID_REQUEST = getString("INVALID_REQUEST");
	
	public String UNAUTHORIZED_USER = getString("UNAUTHORIZED_USER");
}

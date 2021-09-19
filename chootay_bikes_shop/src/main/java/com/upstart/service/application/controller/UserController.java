package com.upstart.service.application.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upstart.service.application.constants.Constants;
import com.upstart.service.application.constants.Messages;
import com.upstart.service.application.entity.User;
import com.upstart.service.application.response.Data;
import com.upstart.service.application.response.ResponseBean;
import com.upstart.service.application.service.UserService;

@RestController
@RequestMapping(value = "user", produces = { MediaType.APPLICATION_JSON_VALUE, "application/hal+json" })
public class UserController extends RestServiceController
{

	@Autowired
	private UserService userService;

	/**
	 * Access Level | Admin & Accounts Manager
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "/create_user")
	public ResponseBean cerateNewUser(@Validated @RequestBody Data data) throws IOException
	{
		log.info("---> Incoming Request: Going to create user entry");

		// if (!Utils.isAllowedToCreateUser(data.getUser()))
		// {
		// return utils.createResponse(Messages.get().UNAUTHORIZED_USER);
		// }

		String message = Messages.get().RECORD_INSERTED_FAILED_MESSAGE;
		Data reponseData = new Data();

		List<User> users = userService.createUser(data.getUsers());

		if (users != null)
		{
			message = Messages.get().RECORD_INSERTED_SUCCESSFUL_MESSAGE;
			reponseData.setUsers(users);
		}

		return utils.createResponse(reponseData, message, Constants.DATABASE_TRANSACTION_SUCCESS_CODE);
	}

	/**
	 * Access Level | Admin & All
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/login")
	public ResponseBean getUserSignIn(@RequestParam(required = true) String username, String password) throws IOException
	{
		log.info("---> Incoming Request: User sign in request with credentials msisdn: {} and password: {}", username, password);

		User user = userService.getUserSignIn(username, password);

		String message = Messages.get().RECORD_NOT_FOUND;
		Data responseData = new Data();

		if (user != null)
		{
			message = Messages.get().LOGIN_AUTHENTICATION_SUCCESSFULL;
			responseData.setUser(user);
		}

		return utils.createResponse(responseData, message, Constants.DATABASE_TRANSACTION_SUCCESS_CODE);

	}

	/**
	 * Access Level | Admin & Accounts Manager
	 * 
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/available_users")
	public ResponseBean getAllUsers() throws IOException
	{
		log.info("---> Incoming Request: Provide available users");

		List<User> users = userService.getAvailableUsers();

		String message = Messages.get().RECORD_NOT_FOUND;
		Data responseData = new Data();

		if (users != null)
		{
			message = Messages.get().RECORD_FOUND_SUCCESSFUL_MESSAGE;
			responseData.setUsers(users);
		}

		return utils.createResponse(responseData, message, Constants.DATABASE_TRANSACTION_SUCCESS_CODE);

	}
}
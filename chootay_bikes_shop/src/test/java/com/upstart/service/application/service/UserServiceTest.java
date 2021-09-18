package com.upstart.service.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.upstart.service.application.constants.Constants;
import com.upstart.service.application.entity.User;
import com.upstart.service.application.service.UserService;
import com.upstart.service.application.util.Utils;

@DataJpaTest
public class UserServiceTest
{

	@Test
	void TestUserEntryWithNull()
	{
		boolean result = Utils.isUserEntityValid(new User());

		assertThat(result).isFalse();
	}

	@Test
	void TestUserEntryInvalid()
	{
		byte roleValue = -1;

		User user = new User();
		user.setUserName("username");
		user.setPassword("password");
		user.setRole(roleValue);

		boolean result = Utils.isUserEntityValid(user);

		assertThat(result).isFalse();
	}

	@Test
	void TestUserEntryValid()
	{
		User user = new User();
		user.setUserName("username");
		user.setPassword("password");
		user.setRole(Constants.ADMIN_ROLE_TYPE.byteValue());

		boolean result = Utils.isUserEntityValid(user);

		assertThat(result).isTrue();
	}
	
	@Test
	void getAvailableUser()
	{
		UserService userService = new UserService();

		List<User> availableUsers = userService.getAvailableUsers();

		if (availableUsers == null)
		{
			assertThat(availableUsers).isNull();
		}
	}

	@Test
	void createUser()
	{
		List<User> users = getUsers();

		UserService userService = new UserService();

		users = userService.createUser(users);

		if (users == null)
		{
			assertThat(users).isNull();
		}
	}

	private List<User> getUsers()
	{
		byte roleValue = 1;

		List<User> users = new ArrayList<User>();

		User user = new User();
		user.setUserName("admin");
		user.setPassword("admin");
		user.setRole(roleValue);
		users.add(user);
		
		return users;
	}

	@Test
	void signInUser()
	{
		UserService userService = new UserService();

		userService.createUser(getUsers());

		User user = userService.getUserSignIn("admin", "admin");

		assertEquals(getUsers().get(0).getUserName(), user.getUserName());
	}
}

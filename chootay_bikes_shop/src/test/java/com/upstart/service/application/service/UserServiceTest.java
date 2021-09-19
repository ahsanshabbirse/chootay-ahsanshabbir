package com.upstart.service.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.upstart.service.application.constants.Constants;
import com.upstart.service.application.entity.User;
import com.upstart.service.application.service.UserService;
import com.upstart.service.application.util.Utils;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserServiceTest
{
	@MockBean
	private UserService userService;

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
		when(userService.getAvailableUsers()).thenReturn(getUsers());

		List<User> availableUsers = userService.getAvailableUsers();

		assertNotNull(availableUsers);
	}

	@Test
	void createUser()
	{
		when(userService.createUser(getUsers())).thenReturn(getUsers());

		List<User> users = userService.createUser(getUsers());

		assertThat(users).isNotNull();
	}

	@Test
	void signInUser()
	{
		String userName = "admin";
		String password = "admin";
		
		when(userService.getUserSignIn(userName, password)).thenReturn(getUser(Constants.ADMIN_ROLE_TYPE.byteValue()));

		User user = userService.getUserSignIn(userName, password);

		assertEquals(user.getUserName(), userName);
	}

	private List<User> getUsers()
	{
		byte roleValue = 1;

		List<User> users = new ArrayList<User>();

		User user = getUser(roleValue);
		users.add(user);

		return users;
	}

	private User getUser(byte roleValue)
	{
		User user = new User();
		user.setUserName("admin");
		user.setPassword("admin");
		user.setRole(roleValue);
		return user;
	}
}

package com.upstart.service.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.net.util.Base64;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.upstart.service.application.controller.UserController;
import com.upstart.service.application.entity.User;
import com.upstart.service.application.response.ResponseBean;
import com.upstart.service.application.service.UserService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ChootayBikesShopApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private UserService userService;

	@Test
	void contextLoads() {
	}

	public HttpHeaders getHeader()
	{
		String plainCreds = "admin:admin";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		return headers;
	}

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {

		
		when(userService.getAvailableUsers()).thenReturn(getUsers());
//		String authorizationHeader = "Basic " + DatatypeConverter.printBase64Binary(("admin" + ":" + "admin").getBytes());
//
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
//        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        requestHeaders.add("Authorization", authorizationHeader);
		
//        HttpEntity<UserController> requestEntity = new HttpEntity<>(new UserController(), requestHeaders);

//        ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("admin", "admin").exchange(
//        		"http://localhost:" + port + "/user/available_users",
//                HttpMethod.GET,
//                requestEntity,
//                String.class
//        );
        
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//        		"http://localhost:" + port + "/user/available_users",
//                HttpMethod.GET,
//                requestEntity,
//                String.class
//        );

//        if (responseEntity.getStatusCode() == HttpStatus.OK)
//        {
//        	System.out.println("OK HO GYA");
//        }
//        
//       System.out.println(responseEntity.getBody());
        
//		HttpEntity<String> request = new HttpEntity<String>(getHeader());
//		assertThat(restTemplate.exchange("http://localhost:" + port + "/user/available_users", HttpMethod.GET, request, String.class)).isNull();
//		
		
		assertThat(this.restTemplate.withBasicAuth("admin", "admin").getForObject("http://localhost:" + port + "/user/available_users",
				String.class)).contains("Unauthorized");
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

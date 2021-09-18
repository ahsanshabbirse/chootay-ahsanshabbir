package com.upstart.service.application.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.upstart.service.application.constants.Constants;
import com.upstart.service.application.service.UserService;

import static com.upstart.service.application.security.ApplicationUserRole.*;
import static com.upstart.service.application.security.ApplicationUserPermission.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter
{

	@Autowired
	private UserService userService;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public ApplicationSecurityConfiguration(PasswordEncoder passwordEncoder)
	{
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{

		http.csrf().disable().authorizeRequests()
		
		.antMatchers("/user/login").hasAuthority(LOGIN.getPermission())
		.antMatchers("/user/create_user").hasAuthority(USER_WRITE.getPermission())
		.antMatchers("/user/available_users").hasAuthority(USER_READ.getPermission())
		
		.antMatchers("/repair_bike/create_bike_repair_detail").hasAuthority(REPAIR_BIKE_WRITE.getPermission())
		.antMatchers("/repair_bike/create_sale_for_bike_repair").hasAuthority(SALES_WRITE.getPermission())
		
		.antMatchers("/spare_part/create_new_spare_part").hasAuthority(SPAREPARTS_WRITE.getPermission())
		.antMatchers("/spare_part/update_spare_part").hasAuthority(SPAREPARTS_WRITE.getPermission())
		.antMatchers("/spare_part/get_spare_parts").hasAuthority(SPAREPARTS_READ.getPermission())
		
		.antMatchers("/sales_report/create_daily_sales_report").hasAuthority(SALES_INVOICE_WRITE.getPermission())
		.antMatchers("/sales_report/create_monthly_sales_report").hasAuthority(SALES_INVOICE_WRITE.getPermission())
		
		.anyRequest().authenticated().and().httpBasic();

	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService()
	{
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		List<com.upstart.service.application.entity.User> users = userService.getAvailableUsers();

		for (com.upstart.service.application.entity.User user : users)
		{
			Set<SimpleGrantedAuthority> authorities = MECHANIC.getGrantedAuthoraties();

			if (Constants.ADMIN_ROLE_TYPE == user.getRole())
			{
				authorities = ADMIN.getGrantedAuthoraties();
			}
			else if (Constants.SALES_PERSON_ROLE_TYPE == user.getRole())
			{
				authorities = SALES_PERSON.getGrantedAuthoraties();
			}
			else if (Constants.ACCOUNT_MANAGER_ROLE_TYPE == user.getRole())
			{
				authorities = ACCOUNT_MANAGER.getGrantedAuthoraties();
			}

			userDetails.add(User.builder().username(user.getUserName()).password(passwordEncoder.encode(user.getPassword())).authorities(authorities).build());
		}

		return new InMemoryUserDetailsManager(userDetails);
	}
}

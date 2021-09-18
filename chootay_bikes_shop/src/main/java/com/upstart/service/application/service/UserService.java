package com.upstart.service.application.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upstart.service.application.entity.User;
import com.upstart.service.application.manager.Manager;
import com.upstart.service.application.util.Utils;

@Service
public class UserService
{
	private Logger log = LoggerFactory.getLogger(UserService.class);
	@Autowired
	protected Utils utils;

	public User getUserSignIn(String username, String password)
	{
		Session session = null;
		try
		{
			session = Manager.getSessionFactory().openSession();
			Query<User> query = session.createQuery("From User WHERE userName = ? AND password = ?");
			query.setParameter(0, username);
			query.setParameter(1, password);
			query.setMaxResults(1);

			if (query.stream().count() < 1)
			{
				log.info("No Record Found");
				return null;
			}

			User user = query.getSingleResult();

			return user;

		}
		catch (Exception ex)
		{
			log.error(ex.toString(), ex);
		}
		finally
		{
			Manager.endSession(session);
		}

		return null;
	}

	public List<User> createUser(List<User> users)
	{
		if (users == null)
		{
			return null;
		}

		log.info("Creating user entry {}", users.toString());

		Session session = null;
		Transaction transaction = null;

		try
		{
			session = Manager.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			for (User user : users)
			{
				if (Utils.isUserEntityValid(user))
				{
					break;
				}
				
				Query<User> availableUser = session.createQuery("From User WHERE userName = ?");
				availableUser.setParameter(0, user.getUserName());
				availableUser.setMaxResults(1);
				
				if(availableUser.stream().count() < 1)
				{
					user.setCreatedTimestamp(new Date());
					session.save(user);

					log.info("New user entry is created against {}", users.toString());
				}
			}

			transaction.commit();

			return users;
		}
		catch (Exception ex)
		{
			log.error(ex.toString(), ex);
		}
		finally
		{
			Manager.endSession(session);
		}

		return null;
	}

	public List<User> getAvailableUsers()
	{

		Session session = null;
		try
		{
			session = Manager.getSessionFactory().openSession();

			List<User> users = session.createQuery("From User").list();

			return users;
		}
		catch (Exception ex)
		{
			log.error(ex.toString(), ex);
		}
		finally
		{
			Manager.endSession(session);
		}

		return null;
	}
}

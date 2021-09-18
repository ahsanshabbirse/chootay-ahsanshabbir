package com.upstart.service.application.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Manager
{

	private static Logger log = LoggerFactory.getLogger(Manager.class);
	private static SessionFactory sessionFactory = null;

	static
	{
		try
		{
			StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
			Metadata meta = new MetadataSources(standardServiceRegistry).getMetadataBuilder().build();
			sessionFactory = meta.getSessionFactoryBuilder().build();

			log.info("session fectory re-initialized");
		}
		catch (Exception e)
		{
			log.error("Exception while initializing hibernate util.. ");
			log.error(e.toString(), e);
		}
	}

	public static SessionFactory getSessionFactory() throws Exception
	{
		return sessionFactory;
	}

	public static void endSession(Session session)
	{
		if (session == null)
			return;
		
		session.close();
		log.info("Information: Connection is Connected, " + session.isConnected());
	}
}

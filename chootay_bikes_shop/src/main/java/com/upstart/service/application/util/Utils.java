package com.upstart.service.application.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.upstart.service.application.constants.Constants;
import com.upstart.service.application.entity.Customer;
import com.upstart.service.application.entity.PurchaseReport;
import com.upstart.service.application.entity.RepairBike;
import com.upstart.service.application.entity.SparePart;
import com.upstart.service.application.entity.User;
import com.upstart.service.application.response.Data;
import com.upstart.service.application.response.PageInfoBean;
import com.upstart.service.application.response.ResponseBean;

@Service
public class Utils
{
	private static Logger log = LoggerFactory.getLogger(Utils.class);

	public ResponseBean createResponse(String message)
	{

		ResponseBean responseBean = new ResponseBean();

		responseBean.setResponseMessage(message);
		responseBean.setResponseCode(null);
		responseBean.setData(null);
		responseBean.setPageInfo(null);

		log.info("<--- Response: " + responseBean.toString());

		return responseBean;
	}

	public ResponseBean createResponse(String message, Integer responseCode)
	{

		ResponseBean responseBean = new ResponseBean();

		responseBean.setResponseMessage(message);
		responseBean.setResponseCode(responseCode);
		responseBean.setData(null);
		responseBean.setPageInfo(null);

		log.info("<--- Response: " + responseBean.toString());

		return responseBean;
	}

	public ResponseBean createResponse(Data data, String message)
	{

		ResponseBean responseBean = new ResponseBean();

		responseBean.setData(data);
		responseBean.setResponseMessage(message);
		responseBean.setResponseCode(null);
		responseBean.setPageInfo(null);

		log.info("<--- Response: " + responseBean.toString());

		return responseBean;
	}

	public ResponseBean createResponse(Data data, String message, Integer responseCode)
	{

		ResponseBean responseBean = new ResponseBean();

		responseBean.setData(data);
		responseBean.setResponseMessage(message);
		responseBean.setResponseCode(responseCode);
		responseBean.setPageInfo(null);

		log.info("<--- Response: " + responseBean.toString());

		return responseBean;
	}

	public ResponseBean createResponse(Data data, Boolean logResponse, String message, Integer responseCode)
	{

		ResponseBean responseBean = new ResponseBean();

		responseBean.setData(data);
		responseBean.setResponseMessage(message);
		responseBean.setResponseCode(responseCode);
		responseBean.setPageInfo(null);

		if (logResponse)
		{
			log.info("<--- Response: " + responseBean.toString());
		}

		return responseBean;
	}

	public ResponseBean createResponse(Data data, PageInfoBean pageInfoBean, String message)
	{

		ResponseBean responseBean = new ResponseBean();

		responseBean.setData(data);
		responseBean.setPageInfo(pageInfoBean);
		responseBean.setResponseMessage(message);
		responseBean.setResponseCode(null);

		log.info("<--- Response: " + responseBean.toString());

		return responseBean;
	}

	public void handleException(Throwable ex, Transaction tx, Logger log)
	{
		log.error(ex.toString(), ex);

		if (tx != null)
			tx.rollback();
	}

	public ResponseBean handleException(Exception ex, Transaction tx, Logger log, String message)
	{
		log.error(ex.toString(), ex);

		if (tx != null)
			tx.rollback();

		return createResponse(message);
	}

	public ResponseBean handleException(Throwable ex, Transaction tx, Logger log, String message, Integer responseCode)
	{
		log.error(ex.toString(), ex);

		if (tx != null)
			tx.rollback();

		return createResponse(message, responseCode);
	}

	public static Date parseDate(String date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			return formatter.parse(date);
		}
		catch (java.text.ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isAllowedToCreateRepairDetails(User user)
	{

		if (user == null)
		{
			return false;
		}

		if (Constants.ADMIN_ROLE_TYPE == user.getRole())
		{
			return true;
		}

		return false;
	}

	public static boolean isAllowedToCreateSales(User user)
	{
		if (user == null)
		{
			return false;
		}

		if (Constants.ADMIN_ROLE_TYPE == user.getRole())
		{
			return true;
		}

		return false;
	}

	public static boolean isAllowedToCreatePurchasesAndInvoices(User user)
	{
		if (user == null)
		{
			return false;
		}

		if (Constants.ADMIN_ROLE_TYPE == user.getRole() || Constants.ACCOUNT_MANAGER_ROLE_TYPE == user.getRole())
		{
			return true;
		}

		return false;
	}

	public static boolean isAllowedToCreateUser(User user)
	{
		if (user == null)
		{
			return false;
		}

		if (Constants.ADMIN_ROLE_TYPE == user.getRole() || Constants.ACCOUNT_MANAGER_ROLE_TYPE == user.getRole())
		{
			return true;
		}

		return false;
	}

	public static boolean isUserEntityValid(User user)
	{

		if (user == null)
		{
			return false;
		}
		
		if (user.getUserName() == null || user.getUserName().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty() || user.getRole() <= 0)
		{
			return false;
		}

		return true;
	}

	public static boolean isBikeEntityValid(RepairBike bike)
	{

		if (bike == null)
		{
			return false;
		}

		if (bike.getPlateNumber() == null || bike.getPlateNumber().isEmpty())
		{
			return false;
		}

		return true;
	}

	public static boolean isSparePartEntityValid(SparePart spareParts)
	{

		if (spareParts == null)
		{
			return false;
		}

		if (spareParts.getName() == null || spareParts.getName().isEmpty())
		{
			return false;
		}

		return true;
	}
	
	public static boolean isCustomerEntityValid(Customer customer)
	{

		if (customer == null)
		{
			return false;
		}

		if (customer.getName() == null || customer.getName().isEmpty() || customer.getAddress() == null || customer.getAddress().isEmpty())
		{
			return false;
		}

		return true;
	}
}

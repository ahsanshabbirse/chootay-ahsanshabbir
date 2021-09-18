package com.upstart.service.application.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upstart.service.application.entity.DailySaleReport;
import com.upstart.service.application.entity.MonthlySaleReport;
import com.upstart.service.application.entity.RepairBike;
import com.upstart.service.application.manager.Manager;
import com.upstart.service.application.util.Utils;

@Service
public class SalesService
{
	private Logger log = LoggerFactory.getLogger(SalesService.class);

	@Autowired
	protected Utils utils;

	public DailySaleReport createDailySalesReport()
	{
		log.info("Generating daily sales report");

		Session session = null;
		Transaction transaction = null;

		try
		{
			session = Manager.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");

			String queryString = "SELECT * From repair_bike WHERE DATE(created_timestamp) = " + dateFormat.format(new Date()) + ";";

			Query<RepairBike> query = session.createNativeQuery(queryString, RepairBike.class);

			List<RepairBike> repairBikes = query.getResultList();

			if (repairBikes == null || repairBikes.isEmpty())
			{
				return null;
			}

			int customerCount = 0;
			Double totalAmount = 0.0;

			DailySaleReport dailySaleReport = session.get(DailySaleReport.class, dateFormat.format(new Date()));

			// check if already created for today
			if (dailySaleReport == null)
			{
				dailySaleReport = new DailySaleReport();
				dailySaleReport.setCustomerCount(0);
				dailySaleReport.setTotalAmountReceived(0);
			}

			for (RepairBike repairBike : repairBikes)
			{
				customerCount++;
				totalAmount = totalAmount + repairBike.getTotalAmount();
			}

			// check if record is added after the invoice is generated
			if (dailySaleReport.getCustomerCount() != customerCount && dailySaleReport.getTotalAmountReceived() != totalAmount)
			{
				dailySaleReport.setCustomerCount(customerCount);
				dailySaleReport.setTotalAmountReceived(totalAmount);

				dailySaleReport.setCreatedTimestamp(new Date());
				session.save(dailySaleReport);
			}

			transaction.commit();

			log.info("Daily Sales Report generated: {}", dailySaleReport.toString());

			return dailySaleReport;

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

	public MonthlySaleReport createMonthlySalesReport()
	{
		log.info("Generating monthly sales report");
		Session session = null;
		Transaction transaction = null;

		try
		{
			session = Manager.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");

			String query = "SELECT * From daily_sale_report WHERE MONTH(report_date) = MONTH('" + dateFormat.format(new Date()) + "');";

			List<DailySaleReport> dailySaleReports = session.createNativeQuery(query, DailySaleReport.class).list();

			if (dailySaleReports == null || dailySaleReports.isEmpty())
			{
				return null;
			}

			int customerCount = 0;
			Double totalAmount = 0.0;

			MonthlySaleReport monthlySaleReport = session.get(MonthlySaleReport.class, dateFormat.format(new Date()));

			// check if already created for today
			if (monthlySaleReport == null)
			{
				monthlySaleReport = new MonthlySaleReport();
				monthlySaleReport.setCustomerCount(0);
				monthlySaleReport.setTotalAmountReceived(0);
			}

			for (DailySaleReport dailySaleReport : dailySaleReports)
			{
				customerCount++;
				totalAmount = totalAmount + dailySaleReport.getTotalAmountReceived();
			}

			// check if record is added after the invoice is generated
			if (monthlySaleReport.getCustomerCount() != customerCount && monthlySaleReport.getTotalAmountReceived() != totalAmount)
			{
				monthlySaleReport.setCustomerCount(customerCount);
				monthlySaleReport.setTotalAmountReceived(totalAmount);

				monthlySaleReport.setCreatedTimestamp(new Date());
				session.save(monthlySaleReport);
			}

			transaction.commit();

			log.info("Monthly Sales Report generated: {}", monthlySaleReport.toString());

			return monthlySaleReport;

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

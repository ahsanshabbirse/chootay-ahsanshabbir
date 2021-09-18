package com.upstart.service.application.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upstart.service.application.constants.Constants;
import com.upstart.service.application.constants.Messages;
import com.upstart.service.application.entity.DailySaleReport;
import com.upstart.service.application.entity.MonthlySaleReport;
import com.upstart.service.application.entity.User;
import com.upstart.service.application.response.Data;
import com.upstart.service.application.response.ResponseBean;
import com.upstart.service.application.service.SalesService;
import com.upstart.service.application.util.Utils;

@RestController
@RequestMapping(value = "sales_report", produces = { MediaType.APPLICATION_JSON_VALUE, "application/hal+json" })
public class SalesController extends RestServiceController
{
	@Autowired
	private SalesService salesService;

	/**
	 * Access Level | Admin & Account Manager
	 * 
	 * @param user
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/create_daily_sales_report")
	private ResponseBean createDailySalesReport(@Validated @RequestBody User user) throws IOException
	{
		log.info("---> Incoming Request: Going to generate daily sales report");

		// if (!Utils.isAllowedToCreatePurchasesAndInvoices(user))
		// {
		// return utils.createResponse(Messages.get().UNAUTHORIZED_USER);
		// }

		String message = Messages.get().RECORD_INSERTED_FAILED_MESSAGE;
		Data responseData = new Data();

		DailySaleReport dailySaleReport = salesService.createDailySalesReport();

		if (dailySaleReport != null)
		{
			message = Messages.get().RECORD_INSERTED_SUCCESSFUL_MESSAGE;
			responseData.setDailySaleReport(dailySaleReport);
		}

		return utils.createResponse(responseData, message, Constants.DATABASE_TRANSACTION_SUCCESS_CODE);
	}

	/**
	 * Access Level | Admin & Accounts Manager
	 * 
	 * @param user
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/create_monthly_sales_report")
	private ResponseBean createMonthlySalesReport(@Validated @RequestBody User user) throws IOException
	{
		log.info("---> Incoming Request: Going to generate monthly sales report");

		// if (!Utils.isAllowedToCreatePurchasesAndInvoices(user))
		// {
		// return utils.createResponse(Messages.get().UNAUTHORIZED_USER);
		// }

		String message = Messages.get().RECORD_INSERTED_FAILED_MESSAGE;
		Data responseData = new Data();

		MonthlySaleReport monthlySaleReport = salesService.createMonthlySalesReport();

		if (monthlySaleReport != null)
		{
			message = Messages.get().RECORD_INSERTED_SUCCESSFUL_MESSAGE;
			responseData.setMonthlySaleReport(monthlySaleReport);
		}

		return utils.createResponse(responseData, message, Constants.DATABASE_TRANSACTION_SUCCESS_CODE);
	}
}
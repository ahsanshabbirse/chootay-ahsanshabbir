package com.upstart.service.application.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upstart.service.application.constants.Constants;
import com.upstart.service.application.constants.Messages;
import com.upstart.service.application.entity.RepairBike;
import com.upstart.service.application.response.Data;
import com.upstart.service.application.response.ResponseBean;
import com.upstart.service.application.service.RepairBikeService;
import com.upstart.service.application.util.Utils;

@RestController
@RequestMapping(value = "repair_bike", produces = { MediaType.APPLICATION_JSON_VALUE, "application/hal+json" })
public class RepairBikeController extends RestServiceController
{

	@Autowired
	private RepairBikeService repairBikeService;

	/**
	 * Access Level | Admin & Mechanic
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "/create_bike_repair_detail")
	private ResponseBean createBikeRepairEntry(@Validated @RequestBody Data data) throws IOException
	{
		log.info("---> Incoming Request: Going to create bike repair entry");

//		if (!Utils.isAllowedToCreateRepairDetails(data.getUser()))
//		{
//			return utils.createResponse(Messages.get().UNAUTHORIZED_USER);
//		}

		if (Utils.isBikeEntityValid(data.getRepairBike()))
		{
			return utils.createResponse(Messages.get().INVALID_REQUEST);
		}

		String message = Messages.get().RECORD_INSERTED_FAILED_MESSAGE;
		Data reponseData = new Data();

		RepairBike repairBike = repairBikeService.createNewRepairBike(data.getRepairBike());

		if (repairBike != null)
		{
			message = Messages.get().RECORD_INSERTED_SUCCESSFUL_MESSAGE;
			reponseData.setRepairBike(repairBike);
		}

		return utils.createResponse(reponseData, message, Constants.DATABASE_TRANSACTION_SUCCESS_CODE);
	}

	/**
	 * Access Level | Admin & Sales Person
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "/create_sale_for_bike_repair")
	private ResponseBean createSaleForBikeRepaired(@Validated @RequestBody Data data) throws IOException
	{
		log.info("---> Incoming Request: Going to add sale on bike repair entry");

//		if (!Utils.isAllowedToCreateSales(data.getUser()))
//		{
//			return utils.createResponse(Messages.get().UNAUTHORIZED_USER);
//		}

		if (!Utils.isCustomerEntityValid(data.getCustomer()))
		{
			return utils.createResponse(Messages.get().INVALID_REQUEST);
		}

		String message = Messages.get().RECORD_INSERTED_FAILED_MESSAGE;
		Data reponseData = new Data();

		RepairBike repairedBike = repairBikeService.createSaleForRepairedBike(data.getCustomer(), data.getRepairBike());

		if (repairedBike != null)
		{
			message = Messages.get().RECORD_INSERTED_SUCCESSFUL_MESSAGE;
			reponseData.setRepairBike(repairedBike);
		}

		return utils.createResponse(reponseData, message, Constants.DATABASE_TRANSACTION_SUCCESS_CODE);
	}
}
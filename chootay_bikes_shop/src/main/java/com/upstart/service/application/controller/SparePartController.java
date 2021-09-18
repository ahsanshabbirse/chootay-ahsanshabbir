package com.upstart.service.application.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upstart.service.application.constants.Constants;
import com.upstart.service.application.constants.Messages;
import com.upstart.service.application.entity.SparePart;
import com.upstart.service.application.response.Data;
import com.upstart.service.application.response.ResponseBean;
import com.upstart.service.application.service.SparePartService;
import com.upstart.service.application.util.Utils;

@RestController
@RequestMapping(value = "spare_part", produces = { MediaType.APPLICATION_JSON_VALUE, "application/hal+json" })
public class SparePartController extends RestServiceController
{
	@Autowired
	private SparePartService sparePartService;

	/**
	 * Access Level | Admin & Accounts Manager
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "/create_new_spare_part")
	private ResponseBean createNewSparePart(@Validated @RequestBody Data data) throws IOException
	{
		log.info("---> Incoming Request: Going to create spare part entry");

		// if (!Utils.isAllowedToCreatePurchasesAndInvoices(data.getUser()))
		// {
		// return utils.createResponse(Messages.get().UNAUTHORIZED_USER);
		// }

		String message = Messages.get().RECORD_INSERTED_FAILED_MESSAGE;
		Data reponseData = new Data();

		SparePart sparePart = sparePartService.createNewSparePart(data.getPurchaseReport());

		if (sparePart != null)
		{
			message = Messages.get().RECORD_INSERTED_SUCCESSFUL_MESSAGE;
			reponseData.setSparePart(sparePart);
		}

		return utils.createResponse(reponseData, message, Constants.DATABASE_TRANSACTION_SUCCESS_CODE);
	}

	/**
	 * Access Level | Admin & Accounts Manager
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "/update_spare_part")
	private ResponseBean updateSparePart(@Validated @RequestBody Data data) throws IOException
	{
		log.info("---> Incoming Request: Going to update spare part entry");

		// if (!Utils.isAllowedToCreatePurchasesAndInvoices(data.getUser()))
		// {
		// return utils.createResponse(Messages.get().UNAUTHORIZED_USER);
		// }

		String message = Messages.get().RECORD_UPDATE_FAILED_MESSAGE;
		Data reponseData = new Data();

		SparePart sparePart = sparePartService.updateSparePart(data.getSparePart());

		if (sparePart != null)
		{
			message = Messages.get().RECORD_UPDATE_SUCCESSFUL_MESSAGE;
			reponseData.setSparePart(sparePart);
		}

		return utils.createResponse(reponseData, message, Constants.DATABASE_TRANSACTION_SUCCESS_CODE);
	}

	/**
	 * Access Level | Admin & Accounts Manager & Mechanic
	 * 
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/get_spare_parts")
	private ResponseBean getAvailableSpareParts() throws IOException
	{
		log.info("---> Incoming Request: Provide available spare parts");

		String message = Messages.get().RECORD_INSERTED_FAILED_MESSAGE;
		Data reponseData = new Data();

		List<SparePart> spareParts = sparePartService.getAvailableSpareParts();

		if (spareParts != null)
		{
			message = Messages.get().RECORD_INSERTED_SUCCESSFUL_MESSAGE;
			reponseData.setSpareParts(spareParts);
		}

		return utils.createResponse(reponseData, message, Constants.DATABASE_TRANSACTION_SUCCESS_CODE);
	}
}
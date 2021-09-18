package com.upstart.service.application.service;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upstart.service.application.constants.Constants;
import com.upstart.service.application.entity.Customer;
import com.upstart.service.application.entity.RepairBike;
import com.upstart.service.application.entity.SparePart;
import com.upstart.service.application.entity.SparePartToRepairBike;
import com.upstart.service.application.manager.Manager;
import com.upstart.service.application.util.Utils;

@Service
public class RepairBikeService
{
	private Logger log = LoggerFactory.getLogger(RepairBikeService.class);
	@Autowired
	protected Utils utils;

	/**
	 * create bike entry and the spare parts allocated: Access Level | Mechanic
	 * @param repairBike
	 * @return
	 */
	public RepairBike createNewRepairBike(RepairBike repairBike)
	{
		log.info("Create bike repair entry {}", repairBike);

		Session session = null;
		Transaction transaction = null;

		try
		{
			session = Manager.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			repairBike.setCreatedTimestamp(new Date());
			session.save(repairBike);

			for (SparePartToRepairBike partToRepairBike : repairBike.getSparePartToRepairBikes())
			{
				partToRepairBike.setRepairBike(repairBike);
				partToRepairBike.setCreatedTimestamp(new Date());
				session.save(partToRepairBike);
			}

			transaction.commit();

			log.info("New Repair Bike entry is created against {}", repairBike.toString());

			return repairBike;
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

	/**
	 * creates sale on repaired bike against spare parts and customer information: Access Level | Sales Person
	 * @param customer
	 * @param repairBike
	 * @return the generated bill
	 */
	public RepairBike createSaleForRepairedBike(Customer customer, RepairBike repairBike)
	{
		log.info("Updating bike repair entry");
		Session session = null;
		Transaction transaction = null;

		try
		{
			session = Manager.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			if (!Utils.isCustomerEntityValid(customer))
			{
				return null;
			}

			// create new customer
			customer.setCreatedTimestamp(new Date());
			session.save(customer);

			// set customer as of bike
			repairBike.setCustomer(customer);

			Double partsTotalAmount = 0.0;
			
			// update the quantity and status of installed spare parts in database
			for (SparePartToRepairBike partToRepairBike : repairBike.getSparePartToRepairBikes())
			{
				SparePart sparePart = session.get(SparePart.class, partToRepairBike.getSparePart().getId());
				sparePart.setQuantity(sparePart.getQuantity() - partToRepairBike.getQuantity());
				partsTotalAmount += sparePart.getCost();
				
				if (sparePart.getQuantity() == 0)
				{
					sparePart.setStatus(Constants.SPARE_PART_STATUS_UNAVAILABLE);
				}
				session.save(sparePart);
			}
			
			repairBike.setPartsTotalAmount(partsTotalAmount);
			repairBike.setTotalAmount(repairBike.getPartsTotalAmount() + repairBike.getLabourAmount());
			
			session.save(repairBike);

			transaction.commit();

			log.info("New user entry is created against {}", repairBike.toString());

			return repairBike;
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

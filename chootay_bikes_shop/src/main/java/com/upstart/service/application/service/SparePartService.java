package com.upstart.service.application.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upstart.service.application.constants.Constants;
import com.upstart.service.application.entity.PurchaseReport;
import com.upstart.service.application.entity.SparePart;
import com.upstart.service.application.manager.Manager;
import com.upstart.service.application.util.Utils;

@Service
public class SparePartService
{
	private Logger log = LoggerFactory.getLogger(SparePartService.class);

	@Autowired
	protected Utils utils;

	public SparePart createNewSparePart(PurchaseReport purchaseReport)
	{
		log.info("Creating spare part entry and purchase report");

		Session session = null;
		Transaction transaction = null;

		try
		{
			// if (!Utils.isSparePartEntityValid(purchaseReport.getSparePart()))
			// {
			// return null;
			// }

			session = Manager.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			SparePart sparePart = purchaseReport.getSparePart();

			if (purchaseReport.getSparePart().getId() != null)
			{
				sparePart = session.get(SparePart.class, purchaseReport.getSparePart().getId());
			}

			sparePart.setStatus(Constants.SPARE_PART_STATUS_UNAVAILABLE);

			sparePart.setQuantity(sparePart.getQuantity() + purchaseReport.getQuantity());

			if (sparePart.getQuantity() > 0)
			{
				sparePart.setStatus(Constants.SPARE_PART_STATUS_AVAILABLE);
			}

			if (sparePart.getCost() < purchaseReport.getPurchaseCost())
			{
				sparePart.setCost(purchaseReport.getPurchaseCost());
			}

			session.save(sparePart);

			purchaseReport.setSparePart(sparePart);
			session.save(purchaseReport);

			transaction.commit();

			log.info("Spare part entry added against {}", sparePart.toString());

			return sparePart;
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

	public SparePart updateSparePart(SparePart newPart)
	{
		log.info("Updating spare part entry");

		Session session = null;
		Transaction transaction = null;

		try
		{
			if (!Utils.isSparePartEntityValid(newPart))
			{
				return null;
			}

			session = Manager.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			SparePart sparePart = session.get(SparePart.class, newPart.getId());

			if (sparePart == null)
			{
				return null;
			}

			sparePart.setQuantity(sparePart.getQuantity() + newPart.getQuantity());
			sparePart.setStatus(Constants.SPARE_PART_STATUS_UNAVAILABLE);

			if (sparePart.getQuantity() > 0)
			{
				sparePart.setStatus(Constants.SPARE_PART_STATUS_AVAILABLE);
			}

			session.save(sparePart);

			transaction.commit();

			log.info("Spare part entry is updated against {}", newPart.toString());

			return sparePart;
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

	public List<SparePart> getAvailableSpareParts()
	{
		log.info("Providing available spare parts");

		Session session = null;

		try
		{
			session = Manager.getSessionFactory().openSession();

			List<SparePart> spareParts = session.createQuery("From SparePart").list();

			if (spareParts.isEmpty())
			{
				return null;
			}

			return spareParts;

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

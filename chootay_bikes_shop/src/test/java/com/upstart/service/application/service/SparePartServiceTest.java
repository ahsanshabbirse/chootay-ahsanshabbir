package com.upstart.service.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.upstart.service.application.constants.Constants;
import com.upstart.service.application.entity.PurchaseReport;
import com.upstart.service.application.entity.SparePart;
import com.upstart.service.application.service.SparePartService;
import com.upstart.service.application.util.Utils;

@DataJpaTest
public class SparePartServiceTest
{

	@Test
	void TestSparePartEntry()
	{
		SparePart sparePart = new SparePart();
		sparePart.setName("Part Name");
		sparePart.setCost(40);

		boolean result = Utils.isSparePartEntityValid(sparePart);

		assertThat(result).isTrue();
	}

	@Test
	void TestCreateSpareParts()
	{
		PurchaseReport purchaseReport = new PurchaseReport(new SparePart("brakes", 10, 300, Constants.SPARE_PART_STATUS_AVAILABLE), 10, 500, new Date());

		SparePartService sparePartService = new SparePartService();

		SparePart savedPart = sparePartService.createNewSparePart(purchaseReport);

		assertEquals(purchaseReport.getSparePart(), savedPart);
	}

	@Test
	void TestUpdateSpareParts()
	{
		PurchaseReport purchaseReport = new PurchaseReport(new SparePart("brakes", 0, 300, Constants.SPARE_PART_STATUS_AVAILABLE), 10, 500, new Date());

		SparePartService sparePartService = new SparePartService();

		SparePart part = sparePartService.createNewSparePart(purchaseReport);

		part.setQuantity(10);

		SparePart updatedPart = sparePartService.updateSparePart(part);

		assertEquals(Constants.SPARE_PART_STATUS_AVAILABLE, updatedPart.getStatus());
	}

	@Test
	void TestUpdateSparePartsWithNullId()
	{
		PurchaseReport purchaseReport = new PurchaseReport(new SparePart("brakes", 0, 300, Constants.SPARE_PART_STATUS_AVAILABLE), 10, 500, new Date());

		SparePartService sparePartService = new SparePartService();

		SparePart part = sparePartService.createNewSparePart(purchaseReport);

		part.setId(null);

		SparePart updatedPart = sparePartService.updateSparePart(part);

		assertThat(updatedPart).isNull();
	}

	@Test
	void TestUpdateSparePartsWithEmptyName()
	{
		PurchaseReport purchaseReport = new PurchaseReport(new SparePart("brakes", 10, 300, Constants.SPARE_PART_STATUS_AVAILABLE), 10, 500, new Date());

		SparePartService sparePartService = new SparePartService();

		SparePart part = sparePartService.createNewSparePart(purchaseReport);

		part.setName("");

		SparePart updatedPart = sparePartService.updateSparePart(part);

		assertThat(updatedPart).isNull();

		// assertEquals(part, updatedPart);

	}

	@Test
	void getAvailableSparePartsTest()
	{
		List<SparePart> availableSpareParts = getAvailableSpareParts();

		for (SparePart part : availableSpareParts)
		{
			assertThat(part.getId()).isNotNull();
		}
	}

	private List<SparePart> getAvailableSpareParts()
	{
		SparePartService sparePartService = new SparePartService();

		List<PurchaseReport> purchaseReports = new ArrayList<PurchaseReport>();

		purchaseReports.add(new PurchaseReport(new SparePart("brakes", 10, 300, Constants.SPARE_PART_STATUS_AVAILABLE), 10, 500, new Date()));
		purchaseReports.add(new PurchaseReport(new SparePart("tyre", 5, 500, Constants.SPARE_PART_STATUS_AVAILABLE), 10, 500, new Date()));
		purchaseReports.add(new PurchaseReport(new SparePart("light", 20, 100, Constants.SPARE_PART_STATUS_AVAILABLE), 10, 500, new Date()));
		
		for (PurchaseReport report : purchaseReports)
		{
			sparePartService.createNewSparePart(report);
		}

		List<SparePart> availableSpareParts = sparePartService.getAvailableSpareParts();
		return availableSpareParts;
	}

}

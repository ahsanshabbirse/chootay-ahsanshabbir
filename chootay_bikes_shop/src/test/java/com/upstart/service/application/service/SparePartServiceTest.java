package com.upstart.service.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.upstart.service.application.constants.Constants;
import com.upstart.service.application.entity.PurchaseReport;
import com.upstart.service.application.entity.SparePart;
import com.upstart.service.application.service.SparePartService;
import com.upstart.service.application.util.Utils;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SparePartServiceTest
{

	@MockBean
	private SparePartService sparePartService;
	
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
	void TestCreateSparePartsPurchaseReport()
	{
		PurchaseReport purchaseReport = getSparePartInPurchaseReport();
		when(sparePartService.createNewSparePart(purchaseReport)).thenReturn(getSparePart());
		
		SparePart savedPart = sparePartService.createNewSparePart(purchaseReport);

		assertEquals(getSparePart().getName(), savedPart.getName());
	}

	private PurchaseReport getSparePartInPurchaseReport()
	{
		return new PurchaseReport(getSparePart(), 10, 500, new Date());
	}
	
	private SparePart getSparePart()
	{
		return new SparePart("brakes", 10, 300, Constants.SPARE_PART_STATUS_AVAILABLE);
	}
	
	@Test
	void TestUpdateSpareParts()
	{
		SparePart sparePart = new SparePart("brakes", 0, 300, Constants.SPARE_PART_STATUS_UNAVAILABLE);
		SparePart updatedSparePart = new SparePart("brakes", 10, 300, Constants.SPARE_PART_STATUS_AVAILABLE);
		
		when(sparePartService.updateSparePart(sparePart)).thenReturn(updatedSparePart);

		SparePart updatedPart = sparePartService.updateSparePart(sparePart);

		assertEquals(Constants.SPARE_PART_STATUS_AVAILABLE, updatedPart.getStatus());
	}

	@Test
	void TestUpdateSparePartsWithNullId()
	{
		SparePart sparePart = new SparePart("brakes", 0, 300, Constants.SPARE_PART_STATUS_UNAVAILABLE);
		sparePart.setId(1);
//		SparePart updatedSparePart = new SparePart("brakes", 10, 300, Constants.SPARE_PART_STATUS_AVAILABLE);
		
		when(sparePartService.updateSparePart(sparePart)).thenReturn(null);
		
		SparePart updatedPart = sparePartService.updateSparePart(sparePart);

		assertThat(updatedPart).isNull();
	}

	@Test
	void TestUpdateSparePartsWithEmptyName()
	{
		SparePart sparePart = new SparePart("brakes", 0, 300, Constants.SPARE_PART_STATUS_UNAVAILABLE);
		SparePart updatedSparePart = new SparePart("", 10, 300, Constants.SPARE_PART_STATUS_AVAILABLE);
		
		when(sparePartService.updateSparePart(sparePart)).thenReturn(updatedSparePart);
		
		SparePart updatedPart = sparePartService.updateSparePart(null);

		assertThat(updatedPart).isNull();
	}

	@Test
	void getAvailableSparePartsTest()
	{
		when(sparePartService.getAvailableSpareParts()).thenReturn(getAvailableSpareParts());
		
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

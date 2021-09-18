package com.upstart.service.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.upstart.service.application.constants.Constants;
import com.upstart.service.application.entity.Customer;
import com.upstart.service.application.entity.DailySaleReport;
import com.upstart.service.application.entity.MonthlySaleReport;
import com.upstart.service.application.entity.PurchaseReport;
import com.upstart.service.application.entity.RepairBike;
import com.upstart.service.application.entity.SparePart;
import com.upstart.service.application.entity.SparePartToRepairBike;
import com.upstart.service.application.service.RepairBikeService;
import com.upstart.service.application.service.SparePartService;
import com.upstart.service.application.util.Utils;

@DataJpaTest
public class RepairBikeServiceTest
{

	private Customer getNewCustomer()
	{
		Customer customer = new Customer();
		customer.setName("name");
		customer.setAddress("address");
		return customer;
	}

	@Test
	void TestBikeEntry()
	{
		RepairBike bike = new RepairBike();
		bike.setPlateNumber("ABC 123");

		boolean result = Utils.isBikeEntityValid(bike);

		assertThat(result).isTrue();
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

	@Test
	void TestCustomerEntry()
	{
		Customer customer = getNewCustomer();

		boolean result = Utils.isCustomerEntityValid(customer);

		assertThat(result).isTrue();
	}
	
	@Test
	void TestCreateRepairBike()
	{
		RepairBike repairedBike = createRepairBike();

		assertThat(repairedBike.getId()).isNotNull();
	}

	private RepairBike createRepairBike()
	{
		RepairBikeService repairBikeService = new RepairBikeService();

		Set<SparePartToRepairBike> partToRepairBikes = new HashSet<SparePartToRepairBike>();

		List<SparePart> availableSpareParts = getAvailableSpareParts();

		for (SparePart part : availableSpareParts)
		{
			SparePartToRepairBike partToRepairBike = new SparePartToRepairBike();
			partToRepairBike.setSparePart(part);
			partToRepairBikes.add(partToRepairBike);
		}
		
		RepairBike repairBike = new RepairBike();
		repairBike.setPlateNumber("ABC 123");

		repairBike.setSparePartToRepairBikes(partToRepairBikes);

		RepairBike repairedBike = repairBikeService.createNewRepairBike(repairBike);
		return repairedBike;
	}
	
	@Test
	void TestCreateSaleOnRepairBike()
	{
		RepairBike updatedBike = createSaleOnRepairedBike();
		
		assertThat(updatedBike.getId()).isNotNull();
		assertTrue(updatedBike.getTotalAmount() >= 1000);
	}

	private RepairBike createSaleOnRepairedBike()
	{
		RepairBikeService repairBikeService = new RepairBikeService();
		
		RepairBike repairedBike = createRepairBike();
		
		repairedBike.setLabourAmount(1000.0);
		
		RepairBike updatedBike = repairBikeService.createSaleForRepairedBike(getNewCustomer(), repairedBike);
		return updatedBike;
	}
	
	@Test
	void TestCreateDailySalesInvoice()
	{
		SalesService salesService = new SalesService();
		
//		RepairBike updatedBike = createSaleOnRepairedBike();
		
		DailySaleReport dailySalesReport = salesService.createDailySalesReport();
	}
	
	@Test
	void TestCreateMonthlySalesInvoice()
	{
		SalesService salesService = new SalesService();
		
//		RepairBike updatedBike = createSaleOnRepairedBike();
		
		MonthlySaleReport monthlySaleReport = salesService.createMonthlySalesReport();
	}
}

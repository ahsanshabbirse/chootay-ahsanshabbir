package com.upstart.service.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.upstart.service.application.constants.Constants;
import com.upstart.service.application.entity.Customer;
import com.upstart.service.application.entity.DailySaleReport;
import com.upstart.service.application.entity.MonthlySaleReport;
import com.upstart.service.application.entity.RepairBike;
import com.upstart.service.application.entity.SparePart;
import com.upstart.service.application.entity.SparePartToRepairBike;
import com.upstart.service.application.service.RepairBikeService;
import com.upstart.service.application.service.SparePartService;
import com.upstart.service.application.util.Utils;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RepairBikeServiceTest
{

	@MockBean
	private RepairBikeService repairBikeService;

	@MockBean
	private SparePartService sparePartService;

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

	private List<SparePart> getSpareParts()
	{
		List<SparePart> spareParts = new ArrayList<SparePart>();
		spareParts.add(new SparePart("brakes", 10, 300, Constants.SPARE_PART_STATUS_AVAILABLE));
		spareParts.add(new SparePart("tyre", 5, 500, Constants.SPARE_PART_STATUS_AVAILABLE));
		spareParts.add(new SparePart("light", 20, 100, Constants.SPARE_PART_STATUS_AVAILABLE));

		return spareParts;
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
		RepairBike newBike = new RepairBike();

		RepairBike bike = createRepairBike();

		when(repairBikeService.createNewRepairBike(newBike)).thenReturn(bike);

		RepairBike repairedBike = repairBikeService.createNewRepairBike(newBike);

		assertThat(repairedBike.getId()).isNotNull();
	}

	private RepairBike createRepairBike()
	{

		Set<SparePartToRepairBike> partToRepairBikes = new HashSet<SparePartToRepairBike>();

		List<SparePart> availableSpareParts = getSpareParts();

		for (SparePart part : availableSpareParts)
		{
			SparePartToRepairBike partToRepairBike = new SparePartToRepairBike();
			partToRepairBike.setSparePart(part);
			partToRepairBikes.add(partToRepairBike);
		}

		RepairBike repairBike = new RepairBike();
		repairBike.setId(1);
		repairBike.setPlateNumber("ABC 123");

		repairBike.setSparePartToRepairBikes(partToRepairBikes);
		return repairBike;
	}

	@Test
	void TestCreateSaleOnRepairBike()
	{
		RepairBike newBike = new RepairBike();

		RepairBike bike = createSaleOnRepairedBike();
		Customer customer = getNewCustomer();

		when(repairBikeService.createSaleForRepairedBike(customer, newBike)).thenReturn(bike);

		RepairBike updatedBike = repairBikeService.createSaleForRepairedBike(customer, newBike);

		assertThat(updatedBike.getId()).isNotNull();
		assertTrue(updatedBike.getTotalAmount() >= 1000);
	}

	private RepairBike createSaleOnRepairedBike()
	{

		RepairBike repairedBike = createRepairBike();

		repairedBike.setLabourAmount(1000.0);
		repairedBike.setTotalAmount(repairedBike.getLabourAmount());
		return repairedBike;
	}

	@Test
	void TestCreateDailySalesInvoice()
	{
		SalesService salesService = new SalesService();

		// RepairBike updatedBike = createSaleOnRepairedBike();

		DailySaleReport dailySalesReport = salesService.createDailySalesReport();
	}

	@Test
	void TestCreateMonthlySalesInvoice()
	{
		SalesService salesService = new SalesService();

		// RepairBike updatedBike = createSaleOnRepairedBike();

		MonthlySaleReport monthlySaleReport = salesService.createMonthlySalesReport();
	}
}

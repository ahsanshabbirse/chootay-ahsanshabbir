package com.upstart.service.application.response;

import java.util.List;

import com.upstart.service.application.entity.Customer;
import com.upstart.service.application.entity.DailySaleReport;
import com.upstart.service.application.entity.MonthlySaleReport;
import com.upstart.service.application.entity.PurchaseReport;
import com.upstart.service.application.entity.RepairBike;
import com.upstart.service.application.entity.SparePart;
import com.upstart.service.application.entity.SparePartToRepairBike;
import com.upstart.service.application.entity.User;

public class Data
{

	private User user;
	private RepairBike repairBike;
	private Customer customer;
	private SparePart sparePart;
	private SparePartToRepairBike sparePartToRepairBike;
	private DailySaleReport dailySaleReport;
	private MonthlySaleReport monthlySaleReport;
	private PurchaseReport purchaseReport;
	private List<User> users;
	private List<RepairBike> repairBikes;
	private List<Customer> customers;
	private List<SparePart> spareParts;
	private List<SparePartToRepairBike> sparePartToRepairBikes;
	private List<DailySaleReport> dailySaleReports;
	private List<MonthlySaleReport> monthlySaleReports;
	private List<PurchaseReport> purchaseReports;

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public RepairBike getRepairBike()
	{
		return repairBike;
	}

	public void setRepairBike(RepairBike repairBike)
	{
		this.repairBike = repairBike;
	}

	public Customer getCustomer()
	{
		return customer;
	}

	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}

	public SparePart getSparePart()
	{
		return sparePart;
	}

	public void setSparePart(SparePart sparePart)
	{
		this.sparePart = sparePart;
	}

	public SparePartToRepairBike getSparePartToRepairBike()
	{
		return sparePartToRepairBike;
	}

	public void setSparePartToRepairBike(SparePartToRepairBike sparePartToRepairBike)
	{
		this.sparePartToRepairBike = sparePartToRepairBike;
	}

	public DailySaleReport getDailySaleReport()
	{
		return dailySaleReport;
	}

	public void setDailySaleReport(DailySaleReport dailySaleReport)
	{
		this.dailySaleReport = dailySaleReport;
	}

	public MonthlySaleReport getMonthlySaleReport()
	{
		return monthlySaleReport;
	}

	public void setMonthlySaleReport(MonthlySaleReport monthlySaleReport)
	{
		this.monthlySaleReport = monthlySaleReport;
	}

	public List<User> getUsers()
	{
		return users;
	}

	public void setUsers(List<User> users)
	{
		this.users = users;
	}

	public List<RepairBike> getRepairBikes()
	{
		return repairBikes;
	}

	public void setRepairBikes(List<RepairBike> repairBikes)
	{
		this.repairBikes = repairBikes;
	}

	public List<Customer> getCustomers()
	{
		return customers;
	}

	public void setCustomers(List<Customer> customers)
	{
		this.customers = customers;
	}

	public List<SparePart> getSpareParts()
	{
		return spareParts;
	}

	public void setSpareParts(List<SparePart> spareParts)
	{
		this.spareParts = spareParts;
	}

	public List<SparePartToRepairBike> getSparePartToRepairBikes()
	{
		return sparePartToRepairBikes;
	}

	public void setSparePartToRepairBikes(List<SparePartToRepairBike> sparePartToRepairBikes)
	{
		this.sparePartToRepairBikes = sparePartToRepairBikes;
	}

	public List<DailySaleReport> getDailySaleReports()
	{
		return dailySaleReports;
	}

	public void setDailySaleReports(List<DailySaleReport> dailySaleReports)
	{
		this.dailySaleReports = dailySaleReports;
	}

	public List<MonthlySaleReport> getMonthlySaleReports()
	{
		return monthlySaleReports;
	}

	public void setMonthlySaleReports(List<MonthlySaleReport> monthlySaleReports)
	{
		this.monthlySaleReports = monthlySaleReports;
	}

	public PurchaseReport getPurchaseReport()
	{
		return purchaseReport;
	}

	public void setPurchaseReport(PurchaseReport purchaseReport)
	{
		this.purchaseReport = purchaseReport;
	}

	public List<PurchaseReport> getPurchaseReports()
	{
		return purchaseReports;
	}

	public void setPurchaseReports(List<PurchaseReport> purchaseReports)
	{
		this.purchaseReports = purchaseReports;
	}

}

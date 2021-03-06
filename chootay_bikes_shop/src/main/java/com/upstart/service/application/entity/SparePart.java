package com.upstart.service.application.entity;
// Generated Sep 18, 2021 7:24:22 PM by Hibernate Tools 5.2.12.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * SparePart generated by hbm2java
 */
@Entity
@Table(name = "spare_part", catalog = "chootay_bikes")
public class SparePart implements java.io.Serializable
{

	private Integer id;
	private String name;
	private int quantity;
	private double cost;
	private byte status;
	@JsonIgnore
	private Set<PurchaseReport> purchaseReports = new HashSet<PurchaseReport>(0);
	@JsonIgnore
	private Set<SparePartToRepairBike> sparePartToRepairBikes = new HashSet<SparePartToRepairBike>(0);

	public SparePart()
	{
	}

	public SparePart(String name, int quantity, double cost, byte status)
	{
		this.name = name;
		this.quantity = quantity;
		this.cost = cost;
		this.status = status;
	}

	public SparePart(String name, int quantity, double cost, byte status, Set<PurchaseReport> purchaseReports, Set<SparePartToRepairBike> sparePartToRepairBikes)
	{
		this.name = name;
		this.quantity = quantity;
		this.cost = cost;
		this.status = status;
		this.purchaseReports = purchaseReports;
		this.sparePartToRepairBikes = sparePartToRepairBikes;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId()
	{
		return this.id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 45)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "quantity", nullable = false)
	public int getQuantity()
	{
		return this.quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	@Column(name = "cost", nullable = false, precision = 22, scale = 0)
	public double getCost()
	{
		return this.cost;
	}

	public void setCost(double cost)
	{
		this.cost = cost;
	}

	@Column(name = "status", nullable = false)
	public byte getStatus()
	{
		return this.status;
	}

	public void setStatus(byte status)
	{
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sparePart")
	public Set<PurchaseReport> getPurchaseReports()
	{
		return this.purchaseReports;
	}

	public void setPurchaseReports(Set<PurchaseReport> purchaseReports)
	{
		this.purchaseReports = purchaseReports;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sparePart")
	public Set<SparePartToRepairBike> getSparePartToRepairBikes()
	{
		return this.sparePartToRepairBikes;
	}

	public void setSparePartToRepairBikes(Set<SparePartToRepairBike> sparePartToRepairBikes)
	{
		this.sparePartToRepairBikes = sparePartToRepairBikes;
	}

}

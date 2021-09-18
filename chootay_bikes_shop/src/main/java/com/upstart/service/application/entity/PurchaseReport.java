package com.upstart.service.application.entity;
// Generated Sep 18, 2021 7:24:22 PM by Hibernate Tools 5.2.12.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PurchaseReport generated by hbm2java
 */
@Entity
@Table(name = "purchase_report", catalog = "chootay_bikes")
public class PurchaseReport implements java.io.Serializable
{

	private Integer id;
	private SparePart sparePart;
	private int quantity;
	private double purchaseCost;
	private Date createdTimestamp;

	public PurchaseReport()
	{
	}

	public PurchaseReport(SparePart sparePart, int quantity, double purchaseCost, Date createdTimestamp)
	{
		this.sparePart = sparePart;
		this.quantity = quantity;
		this.purchaseCost = purchaseCost;
		this.createdTimestamp = createdTimestamp;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spare_part_id", nullable = false)
	public SparePart getSparePart()
	{
		return this.sparePart;
	}

	public void setSparePart(SparePart sparePart)
	{
		this.sparePart = sparePart;
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

	@Column(name = "purchase_cost", nullable = false, precision = 22, scale = 0)
	public double getPurchaseCost()
	{
		return this.purchaseCost;
	}

	public void setPurchaseCost(double purchaseCost)
	{
		this.purchaseCost = purchaseCost;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_timestamp", nullable = false, length = 19)
	public Date getCreatedTimestamp()
	{
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp)
	{
		this.createdTimestamp = createdTimestamp;
	}

}

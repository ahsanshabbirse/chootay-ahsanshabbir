package com.upstart.service.application.entity;
// Generated Sep 18, 2021 7:24:22 PM by Hibernate Tools 5.2.12.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DailySaleReport generated by hbm2java
 */
@Entity
@Table(name = "daily_sale_report", catalog = "chootay_bikes")
public class DailySaleReport implements java.io.Serializable
{

	private Date reportDate;
	private int customerCount;
	private double totalAmountReceived;
	private Date createdTimestamp;

	public DailySaleReport()
	{
	}

	public DailySaleReport(Date reportDate, int customerCount, double totalAmountReceived, Date createdTimestamp)
	{
		this.reportDate = reportDate;
		this.customerCount = customerCount;
		this.totalAmountReceived = totalAmountReceived;
		this.createdTimestamp = createdTimestamp;
	}

	@Id

	@Temporal(TemporalType.DATE)
	@Column(name = "report_date", unique = true, nullable = false, length = 10)
	public Date getReportDate()
	{
		return this.reportDate;
	}

	public void setReportDate(Date reportDate)
	{
		this.reportDate = reportDate;
	}

	@Column(name = "customer_count", nullable = false)
	public int getCustomerCount()
	{
		return this.customerCount;
	}

	public void setCustomerCount(int customerCount)
	{
		this.customerCount = customerCount;
	}

	@Column(name = "total_amount_received", nullable = false, precision = 22, scale = 0)
	public double getTotalAmountReceived()
	{
		return this.totalAmountReceived;
	}

	public void setTotalAmountReceived(double totalAmountReceived)
	{
		this.totalAmountReceived = totalAmountReceived;
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

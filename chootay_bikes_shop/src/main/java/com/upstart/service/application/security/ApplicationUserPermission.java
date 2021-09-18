package com.upstart.service.application.security;

public enum ApplicationUserPermission
{
	USER_READ("user:read"),
	USER_WRITE("user:write"),
	SPAREPARTS_READ("sparepart:read"),
	SPAREPARTS_WRITE("sparepart:write"),
	REPAIR_BIKE_READ("repair_bike:read"),
	REPAIR_BIKE_WRITE("repair_bike:write"),
	SALES_READ("sales:read"),
	SALES_WRITE("sales:write"),
	SALES_INVOICE_READ("sales_invoice:read"),
	SALES_INVOICE_WRITE("sales_invoice:write"),
	LOGIN("login");
	
	private final String permission;
	
	ApplicationUserPermission(String permission)
	{
		this.permission = permission;
	}
	
	public String getPermission()
	{
		return permission;
	}
}

package com.upstart.service.application.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

import static com.upstart.service.application.security.ApplicationUserPermission.*;

public enum ApplicationUserRole
{
	ADMIN(Sets.newHashSet(LOGIN, USER_READ, USER_WRITE, SPAREPARTS_READ, SPAREPARTS_WRITE, REPAIR_BIKE_READ, REPAIR_BIKE_WRITE, SALES_READ, SALES_WRITE, SALES_INVOICE_READ, SALES_INVOICE_WRITE)), 
	MECHANIC(Sets.newHashSet(LOGIN, REPAIR_BIKE_READ, REPAIR_BIKE_WRITE)), 
	SALES_PERSON(Sets.newHashSet(LOGIN, SALES_READ, SALES_WRITE)), 
	ACCOUNT_MANAGER(Sets.newHashSet(LOGIN, USER_READ, USER_WRITE, SALES_INVOICE_READ, SALES_INVOICE_WRITE, SPAREPARTS_READ, SPAREPARTS_WRITE));

	private final Set<ApplicationUserPermission> permissions;

	ApplicationUserRole(Set<ApplicationUserPermission> permissions)
	{
		this.permissions = permissions;
	}

	public Set<ApplicationUserPermission> getPermissions()
	{
		return permissions;
	}

	public Set<SimpleGrantedAuthority> getGrantedAuthoraties()
	{
	 Set<SimpleGrantedAuthority> permissions = getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
	 permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
	 return permissions;
	}
	
}

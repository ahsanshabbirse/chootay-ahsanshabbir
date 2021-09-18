package com.upstart.service.application.constants;

import uk.co.planetbeyond.util.CustomResourceBundle;

public class Constants extends CustomResourceBundle
{
	public Constants()
	{
		super("service");
	}

	private static Constants instance = null;

	public static synchronized Constants get()
	{
		if (instance == null || instance.reloadProperties())
		{
			instance = new Constants();
		}

		return instance;
	}

	// Specify the machine origin which is hosting the running application
	public final String ACCESS_CONTROL_ALLOW_ORIGIN = getString("ACCESS_CONTROL_ALLOW_ORIGIN");
	public final String ACCESS_CONTROL_ALLOW_CREDENTIALS = getString("ACCESS_CONTROL_ALLOW_CREDENTIALS");
	public static final String CROSS_ORIGIN = "*";

	// Application Response Codes
	public static Integer DATABASE_TRANSACTION_SUCCESS_CODE = 200;
	public static Integer DATABASE_TRANSACTION_FAILURE_CODE = 400;
	public static Integer EMAIL_DELIVERED_SUCCESS_CODE = 200;
	public static Integer EMAIL_DELIVERED_FAILURE_CODE = 400;

	public static final Integer RESPONSE_CODE_FAILURE = 0;
	public static final Integer RESPONSE_CODE_SUCCESS = 1;
	public static final Integer RESPONSE_CODE_RESTRICTED = 2;


	public static final byte SPARE_PART_STATUS_UNAVAILABLE = 0;
	public static final byte SPARE_PART_STATUS_AVAILABLE = 1;

	public static final Integer ADMIN_ROLE_TYPE = 1;
	public static final Integer MECHANIC_ROLE_TYPE = 2;
	public static final Integer SALES_PERSON_ROLE_TYPE = 3;
	public static final Integer ACCOUNT_MANAGER_ROLE_TYPE = 4;
	
	public static final Integer MECHANIC_LEVEL_ACCESS = 1;
	public static final Integer SALES_PERSON_LEVEL_ACCESS = 2;
	public static final Integer ACCOUNT_MANAGER_LEVEL_ACCESS = 3;
	public static final Integer ADMIN_LEVEL_ACCESS = 4;
	
	
	public static final String ADMIN_ROLE_STRING = "ADMIN";
	public static final String USER_ROLE_STRING = "USER";
	
	public static final String ADMIN_LEVEL_PERMISSION_STRING = "ADMIN";
	public static final String MECHANIC_LEVEL_PERMISSION_STRING = "MECHANIC";
	public static final String SALES_PERSON_LEVEL_PERMISSION_STRING = "SALES_PERSON";
	public static final String ACCOUNT_MANAGER_PERMISSION_STRING = "ACCOUNT_MANAGER";

}

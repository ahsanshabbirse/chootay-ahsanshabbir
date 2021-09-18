# chootay-ahsanshabbir
Chootay Bikes Shop Web Services

# Configurations
You need to pull the source code in IDE.

Database model in mysql is available in root/resources, sync it and configure the url in hibernate.cfg.xml file.


Embeded tomcat will run the service on default port 8080.


Pre-defined permissions are added in file name ApplicationUserRole under the package com.upstart.service.application.security against each role and also can be modified.

# Running
As spring security is used to secure the web services.

You must add user records with pre-defined roles in the database, otherwise web service response would be unauthorized or forbiden. 

As of basic authentication used in service, every request must contains the username and password.


The following URL's can be called after the service is up:

# GET Request
This request will allow user to login.
http://localhost:8080/user/login?username=admin&password=admin

This request will return list of available user logins.
http://localhost:8080/user/available_users

This request will return available/purchased spare parts in the system.
http://localhost:8080/spare_part/get_spare_parts

# POST Request
Creates user entry.
http://localhost:8080/user/create_user

Creates bike to be repaired entry.
http://localhost:8080/repair_bike/create_bike_repair_detail

Create sale/generate bill against bike repaired.
http://localhost:8080/repair_bike/create_sale_for_bike_repair

Add spare part in the system.
http://localhost:8080/spare_part/create_new_spare_part

Update existing spare part/quantity.
http://localhost:8080/spare_part/update_spare_part

Generate daily sales report.
http://localhost:8080/sales_report/create_daily_sales_report

Generate monthly sales report.
http://localhost:8080/sales_report/create_monthly_sales_report

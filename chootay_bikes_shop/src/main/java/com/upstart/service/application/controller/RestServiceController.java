package com.upstart.service.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.upstart.service.application.constants.Constants;
import com.upstart.service.application.util.Utils;

@CrossOrigin(value = Constants.CROSS_ORIGIN)
public abstract class RestServiceController
{
	protected Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	protected Utils utils;
}

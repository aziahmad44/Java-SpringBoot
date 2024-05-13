package com.duaz.microservices.admin.constant;

import java.util.Calendar;

public class RIConstants {
	public static final String PROJECT_NAME = "MICROSERVICES-ADMIN";

	public static final String VERSION = "v1.0.0";
	
	public static final String DEFAULT_CONTEXT = "/duaz";
	
	public static final int SYSTEM_USER = 0;

	/** REST_STATUS */
	public static final int REST_SUCCESS_STATUS = 1;
	public static final int REST_FAIL_STATUS = 0;
	
	/** REST API LOGIN */
	public static final int REST_API_LOGIN_SUCCESS = 1;
	public static final int REST_API_LOGIN_WRONG_PASSWORD = 2;
	public static final int REST_API_LOGIN_USER_NOT_FOUND = 3;
	public static final int REST_API_LOGIN_USER_SUSPENDED = 4;

	/** STATUS_VALUE */
	public static final String STATUS_ACTIVE = "Active";
	public static final String STATUS_INACTIVE = "Inactive";
	
	public static final String YES = "Yes";
	public static final String NO = "No";
	
	/** LANGUAGE_ID */
	public static final String LANGUAGE_EN = "EN";
	public static final String LANGUAGE_ID = "ID";
	
	/** SQL Validate */
	public static final String IN = "IN";
	public static final String NOT_IN = "NOT_IN";
}




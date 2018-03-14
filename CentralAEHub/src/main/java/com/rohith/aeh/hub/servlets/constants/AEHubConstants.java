package com.rohith.aeh.hub.servlets.constants;

public class AEHubConstants {
	
	/**
	 * Status
	 */
	public static final String REGISTRATION_DONE = "SUCCESS";

	public static final String REGISTRATION_FAILED = "FAILED";
	
	/**
	 * 
	 * Headers
	 */
	
	public static final String ERROR_MAPPING = "x-error-mapping";
	
	public static final String CLIENT_NAME_HEADER = "x-client-name";
	
	public static final String CLIENT_SECRET_HEADER = "x-client-secret";

	public static final String CLIENT_NAME_STRING = "x-client-name";
	
	public static final String CLIENT_VALIDATION_RESPONSE_HEADER = "x-auth-response";
	
	public static final String CLIENT_VALIDATION_RESPONSE_ERROR ="x-auth-response-error";

	public static final String CLIENT_REGISTRATION_STATUS_HEADER = "x-client-reg-status";
	
	public static final String LOGIN_PAGE_REDIRECTION_URL="x-login-redirect";
	
	public static final String AUTH_GRANT_HEADER="x-auth-grant";

	public static final String ACCESS_TOKEN_HEADER = "x-bearer-token";
	
	public static final String ACCESS_SCOPE_REQUEST = "x-request-scope";
	
	public static final String CLIENT_USER_NAME_PARAM = "x-client-authid";

	public static final String CLIENT_SECURE_PASSWORD_PARAM = "x-client-pass";
	
	public static final String REQUEST_SCOPE_TYPE ="x-request-scope-type";
	/**
	 * ERROR Codes
	 * 
	 */
	public static final String CLIENT_NAME_NOT_VALID_ERROR = "Invalid Client Name";

	public static final String CLIENT_SECRET_NOT_GENERATED = "Could not register client";
	
	
	
}

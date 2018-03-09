package com.rohith.app.authclient.constants;

public class AEHClientConstants {

	
	public static final String DEFAULT_SCHEME="http://";
	
	
	/**
	 * Status
	 */
	public static final String REGISTRATION_DONE = "SUCCESS";

	public static final String REGISTRATION_FAILED = "FAILED";

	/**
	 * Header
	 * 
	 */

	public static final String BEARER_TOKEN = "x-bearer-token";

	public static final String REQUEST_SCOPE = "x-request-scope";
	
	public static final String REQUEST_SCOPE_TYPE ="x-request-scope-type";

	public static final String CLIENT_NAME_HEADER = "x-client-name";

	public static final String CLIENT_SECRET_HEADER = "x-client-secret";

	public static final String SERVER_ACCESS_AUTH_RESPONSE = "x-auth-response";

	public static final String CLIENT_REDIRECT_HEADER = "x-redirect-required";

	public static final String CLIENT_REDIRECT_URL = "x-clredirect-url";

	public static final String SERVER_REDIRECT_URL = "x-login-redirect";

	public static final String CLIENT_ACCESS_TYPE_HEADER = "x-request-type";

	public static final String CLIENT_REDIRECT_STATUS = "x-clredirect-status";

	public static final String AUTH_GRANT_HEADER = "x-auth-grant";

	public static final String CLIENT_REGISTRATION_STATUS_HEADER = "x-client-reg-status";

	public static final String CLIENT_USER_NAME_PARAM = "x-client-authid";

	public static final String CLIENT_SECURE_PASSWORD_PARAM = "x-client-pass";
	
	public static final String ACCESS_TOKEN_HEADER = "x-bearer-token";
}

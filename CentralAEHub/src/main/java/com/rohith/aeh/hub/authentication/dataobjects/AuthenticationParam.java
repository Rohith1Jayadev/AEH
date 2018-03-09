package com.rohith.aeh.hub.authentication.dataobjects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationParam extends AEHAuthParamBase{

	private AuthenticationResponse authResponse;

	public AuthenticationParam(HttpServletRequest request, HttpServletResponse response) {
		super(request,response);
	}
	
	public AuthenticationResponse getAuthResponse() {
		return authResponse;
	}

	public void setAuthResponse(AuthenticationResponse authResponse) {
		this.authResponse = authResponse;
	}

}

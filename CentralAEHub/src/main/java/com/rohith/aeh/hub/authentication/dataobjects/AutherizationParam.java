package com.rohith.aeh.hub.authentication.dataobjects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AutherizationParam extends AEHAuthParamBase{

	private AutherizationResponse autherizationResponse;
	
	public AutherizationParam(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		
	}

	public AutherizationResponse getAutherizationResponse() {
		return autherizationResponse;
	}

	public void setAutherizationResponse(AutherizationResponse autherizationResponse) {
		this.autherizationResponse = autherizationResponse;
	}

	
}

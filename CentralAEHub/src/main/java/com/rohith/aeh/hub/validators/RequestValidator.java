package com.rohith.aeh.hub.validators;

import javax.servlet.http.HttpServletRequest;

import com.rohith.aeh.hub.exception.AEHHubException;

public interface RequestValidator {

	
	public boolean validate(HttpServletRequest request) throws AEHHubException;
	 
}

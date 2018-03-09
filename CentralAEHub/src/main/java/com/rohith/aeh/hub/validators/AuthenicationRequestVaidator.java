package com.rohith.aeh.hub.validators;

import javax.servlet.http.HttpServletRequest;

import com.rohith.aeh.hub.exception.AEHHubException;

public class AuthenicationRequestVaidator extends ParamValidator {

	public boolean validate(HttpServletRequest request) throws AEHHubException {

		boolean superValidator = super.validate(request);

		if (!superValidator) {
			return false;
		
		}
		return true;
	}

}

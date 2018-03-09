package com.rohith.aeh.hub.validators;

import javax.servlet.http.HttpServletRequest;

import com.rohith.aeh.hub.exception.AEHHubException;

/**
 * <p>
 * This validator should be used by the Access Request Validation
 * 
 * class
 * </p>
 * .
 * <p>
 * The request floats through a chain of request validators which
 * 
 * validates various aspects of the request.
 * </p>
 * 
 * @author Accolite
 *
 */
public class AccessGrantValidator extends ScopeValidator {

	public boolean validate(HttpServletRequest request) throws AEHHubException {

		boolean superValidation = super.validate(request);

		if (!superValidation) {
			return false;
		}
		return true;
	}

}

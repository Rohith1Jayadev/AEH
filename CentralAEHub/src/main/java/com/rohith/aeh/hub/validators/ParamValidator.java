package com.rohith.aeh.hub.validators;

import javax.servlet.http.HttpServletRequest;

import com.rohith.aeh.hub.exception.AEHHubException;
import com.rohith.aeh.hub.servlets.constants.AEHubConstants;
import com.rohith.aeh.hub.util.param.ParamUtil;

public class ParamValidator extends ClientSecretValidator {

	public boolean validate(HttpServletRequest request) throws AEHHubException {
		boolean superValidation = super.validate(request);
		if (!superValidation) {
			return false;
		}
		return validateParams(request);
	}

	/**
	 * 
	 * Validates the user email and passcode before they are being sent for 
	 * 
	 * external authentication
	 * 
	 * @param request
	 * @return
	 */
	private boolean validateParams(HttpServletRequest request) {

		String userEmail = request.getParameter(AEHubConstants.CLIENT_USER_NAME_PARAM);

		String passcode = request.getParameter(AEHubConstants.CLIENT_SECURE_PASSWORD_PARAM);

		if (ParamUtil.validateUserEmail(userEmail)) {

			if (ParamUtil.validateSecurePasscode(passcode)) {
				return true;
			}
		}
		return false;
	}

}

package com.rohith.aeh.hub.validators;

import javax.servlet.http.HttpServletRequest;

import com.rohith.aeh.hub.exception.AEHHubException;
import com.rohith.aeh.hub.servlets.constants.AEHubConstants;
import com.rohith.aeh.hub.servlets.constants.AccessGrantErrorCodes;

public class RefreshTokenValidator extends ClientSecretValidator {

	
	public boolean validate(HttpServletRequest request) throws AEHHubException{
		boolean superValidation = super.validate(request);
		if (!superValidation) {
			return false;
		}
		return validateRefreshToken(request);
	}
	private boolean validateRefreshToken(HttpServletRequest request) {
		String accessheader = request.getHeader(AEHubConstants.ACCESS_TOKEN_HEADER);
		if (null == accessheader || "null".equals(accessheader)) {
			request.setAttribute(AEHubConstants.ERROR_MAPPING, AccessGrantErrorCodes.INVALID_TOKEN_KEY);
			return false;
		}
		return false;
	}
}

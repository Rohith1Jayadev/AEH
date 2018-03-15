package com.rohith.aeh.hub.validators;

import javax.servlet.http.HttpServletRequest;

import com.rohith.aeh.hub.exception.AEHHubException;
import com.rohith.aeh.hub.servlets.constants.AEHubConstants;
import com.rohith.aeh.hub.servlets.constants.AccessGrantErrorCodes;
import com.rohith.aeh.hub.util.token.BearerToken;
import com.rohith.aeh.hub.util.token.TokenUtil;

public class TokenKeyValidator extends ClientSecretValidator {

	public boolean validate(HttpServletRequest request) throws AEHHubException {

			boolean superValidation = super.validate(request);
		if (!superValidation) {
			return false;
		}
		return validateTokenKey(request);
	}

	/**
	 * Token Key Validation Logic
	 * Actual Implementation may vary based on extra subjects added to the token
	 * @param request
	 * @return
	 * @throws AEHHubException
	 */
	private boolean validateTokenKey(HttpServletRequest request) throws AEHHubException {
		String accessheader = request.getHeader(AEHubConstants.ACCESS_TOKEN_HEADER);
		String clientSecret = request.getHeader(AEHubConstants.CLIENT_SECRET_HEADER);
		if (null == accessheader || "null".equals(accessheader)) {
			request.setAttribute(AEHubConstants.ERROR_MAPPING, AccessGrantErrorCodes.INVALID_TOKEN_KEY);
			return false;
		}
		BearerToken tokenValue = null;
		try {
			tokenValue = TokenUtil.getTokenValue(accessheader);
		} catch (AEHHubException e) {
			if (e.getType() == AccessGrantErrorCodes.EXPIRED_TOKEN_KEY) {
				request.setAttribute(AEHubConstants.ERROR_MAPPING, AccessGrantErrorCodes.EXPIRED_TOKEN_KEY);
				return false;
			} else {
				request.setAttribute(AEHubConstants.ERROR_MAPPING, AccessGrantErrorCodes.INVALID_TOKEN_KEY);
				return false;
			}
		}
		if (TokenUtil.hasExpired(tokenValue)) {
			request.setAttribute(AEHubConstants.ERROR_MAPPING, AccessGrantErrorCodes.EXPIRED_TOKEN_KEY);
			return false;
		}
		if (TokenUtil.isClientValidated(tokenValue, clientSecret)) {
			return true;
		}
		request.setAttribute(AEHubConstants.ERROR_MAPPING, AccessGrantErrorCodes.INVALID_TOKEN_KEY);
		return false;
	}
}

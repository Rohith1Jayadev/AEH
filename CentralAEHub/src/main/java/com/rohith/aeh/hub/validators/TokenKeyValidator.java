package com.rohith.aeh.hub.validators;

import javax.servlet.http.HttpServletRequest;

import com.rohith.aeh.hub.exception.AEHHubException;
import com.rohith.aeh.hub.servlets.constants.AEHubConstants;
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
	 * 
	 * Actual Implementation may vary based on extra subjects added to the token
	 * 
	 * @param request
	 * @return
	 * @throws AEHHubException 
	 */
	private boolean validateTokenKey(HttpServletRequest request) throws AEHHubException {
	
		String accessheader = request.getHeader(AEHubConstants.ACCESS_TOKEN_HEADER);
		String clientSecret = request.getHeader(AEHubConstants.CLIENT_SECRET_HEADER);
		if (null == accessheader || "null".equals(accessheader)) {
			return false;
		}
		BearerToken tokenValue = TokenUtil.getTokenValue(accessheader);
		if (TokenUtil.hasExpired(tokenValue)) {
			boolean refreshTokenValidation = validateRefreshToken();
			if(!refreshTokenValidation){
				return false;
			}
			return true;
		}
		if (TokenUtil.isClientValidated(tokenValue, clientSecret)) {
			return true;
		}
		return false;
	}
	private boolean validateRefreshToken() {
		return true;
	}

}

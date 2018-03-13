package com.rohith.aeh.hub.validators;

import javax.servlet.http.HttpServletRequest;

import com.rohith.aeh.hub.exception.AEHHubException;
import com.rohith.aeh.hub.servlets.constants.AEHubConstants;
import com.rohith.aeh.hub.servlets.constants.AccessGrantErrorCodes;
import com.rohith.aeh.hub.util.token.BearerToken;
import com.rohith.aeh.hub.util.token.Scope;
import com.rohith.aeh.hub.util.token.TokenUtil;

public class ScopeValidator extends TokenKeyValidator {

	@Override
	public boolean validate(HttpServletRequest request) throws AEHHubException {
		boolean superValidation = super.validate(request);
		if (!superValidation) {
			return false;
		}
		return validateScope(request);
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws AEHHubException
	 */
	private boolean validateScope(HttpServletRequest request) throws AEHHubException {
		String accessheader = request.getHeader(AEHubConstants.ACCESS_TOKEN_HEADER);
		String requestedScope = request.getHeader(AEHubConstants.ACCESS_SCOPE_REQUEST);
		String requestedType = request.getHeader(AEHubConstants.REQUEST_SCOPE_TYPE);
		if (null == accessheader || null == requestedScope || "null".equals(accessheader)
				|| "null".equals(requestedScope)) {
			request.setAttribute(AEHubConstants.ERROR_MAPPING, AccessGrantErrorCodes.INVALID_SCOPE);
			return false;
		}
		BearerToken tokenValue = TokenUtil.getTokenValue(accessheader);
		if (TokenUtil.hasScope(tokenValue, Scope.fromRequestedScope(requestedScope, requestedType))) {
			return true;
		}
		request.setAttribute(AEHubConstants.ERROR_MAPPING, AccessGrantErrorCodes.SCOPE_NOT_GRANTED);
		return false;
	}

}

package com.rohith.aeh.hub.validators;

import javax.servlet.http.HttpServletRequest;

import com.rohith.aeh.hub.exception.AEHHubException;
import com.rohith.aeh.hub.servlets.constants.AEHubConstants;
import com.rohith.aeh.hub.servlets.constants.AccessGrantErrorCodes;
import com.rohith.aeh.hub.servlets.constants.ClientContainer;

public class ClientSecretValidator implements RequestValidator {

	public boolean validate(HttpServletRequest request) throws AEHHubException {
		String clientSecret = request.getHeader(AEHubConstants.CLIENT_SECRET_HEADER);
		String clientName= 	  request.getHeader(AEHubConstants.CLIENT_NAME_STRING);
		boolean validateClientSecret = validateClientSecret(clientSecret,clientName,request);
		if(!validateClientSecret){
			return false;
		}
		return true;
	}
	private boolean validateClientSecret(String clientSecret, String clientName , HttpServletRequest request) throws AEHHubException {
		if (null == clientName || null == clientSecret || "".equals(clientSecret) || "".equals(clientName)) {
			request.setAttribute(AEHubConstants.ERROR_MAPPING, AccessGrantErrorCodes.INVALID_CLIENT_SECRET);
			return false;
		} else {
			ClientContainer container = ClientContainer.container();
			if (container.isPresent(clientSecret, clientName)) {
				return true;
			} else {
				request.setAttribute(AEHubConstants.ERROR_MAPPING, AccessGrantErrorCodes.CLIENT_NOT_PRESENT);
				return false;
			}
		}
	}
	
}

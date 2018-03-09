package com.rohith.aeh.hub.authentication;

import com.rohith.aeh.hub.authentication.dataobjects.AuthenticationParam;
import com.rohith.aeh.hub.authentication.dataobjects.AuthenticationResponse;
import com.rohith.aeh.hub.authentication.dataobjects.ResponseType;
import com.rohith.aeh.hub.util.authgrant.AuthGrantUtil;

/**
 * 
 * This is the Authenitcator implementation that uses the GOOGLE API for
 * authentication
 * 
 * @author Accolite
 *
 */
public class GoogleAPIAuthenticator extends RequestAuthenticator {

	@Override
	public void authenticate(AEHAuthCallBack callBack) {

		AuthenticationParam param = (AuthenticationParam) callBack.getParam();

		AuthenticationResponse response = new AuthenticationResponse();

		response.setAuthGrant(AuthGrantUtil.buildAuthGrant(param).toTokenString());

		response.setResponseCode(200);

		response.setResponse(ResponseType.SUCCESS);

		param.setAuthResponse(response);

		this.divert(callBack, true);

	}

}

package com.rohith.aeh.hub.authentication;

import javax.servlet.http.HttpServletResponse;

import com.rohith.aeh.hub.authentication.dataobjects.AuthenticationParam;
import com.rohith.aeh.hub.authentication.dataobjects.AuthenticationResponse;
import com.rohith.aeh.hub.authentication.dataobjects.ResponseType;
import com.rohith.aeh.hub.servlets.constants.AEHubConstants;

public class AuthenticationCallBack implements AEHAuthCallBack {

	private AuthenticationParam param;

	public AuthenticationCallBack(AuthenticationParam param) {

		this.param = param;
	}

	@Override
	public void onSuccess() {

		HttpServletResponse response = param.getResponse();

		AuthenticationResponse authenticationResponse = this.param.getAuthResponse();

		if (null == authenticationResponse) {

			response.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_HEADER, "false");
			response.setStatus(500);
			response.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_ERROR, "Couldnt complete validation");
		}

		if (authenticationResponse.getResponse() == ResponseType.SUCCESS) {
			response.setHeader(AEHubConstants.AUTH_GRANT_HEADER, authenticationResponse.getAuthGrant());
			response.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_HEADER, "true");
		}

	}

	@Override
	public void onFailure() {

		HttpServletResponse response = param.getResponse();

		AuthenticationResponse authenticationResponse = this.param.getAuthResponse();

		if (null == authenticationResponse) {

			response.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_HEADER, "false");
			response.setStatus(500);
			response.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_ERROR, "Couldnt complete validation");
		}

		if (authenticationResponse.getResponse() == ResponseType.ERROR
				|| authenticationResponse.getResponse() == ResponseType.UNKNOWN) {

			response.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_HEADER, "false");
			response.setStatus(authenticationResponse.getResponseCode());
			response.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_ERROR,
					authenticationResponse.getErrorReason());
		}

	}

	@Override
	public AuthenticationParam getParam() {

		return this.param;
	}

}

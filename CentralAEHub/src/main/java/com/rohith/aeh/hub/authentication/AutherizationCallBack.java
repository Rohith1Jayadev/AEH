package com.rohith.aeh.hub.authentication;

import javax.servlet.http.HttpServletResponse;

import com.rohith.aeh.hub.authentication.dataobjects.AutherizationParam;
import com.rohith.aeh.hub.authentication.dataobjects.AutherizationResponse;
import com.rohith.aeh.hub.authentication.dataobjects.ResponseType;
import com.rohith.aeh.hub.exception.AEHHubException;
import com.rohith.aeh.hub.servlets.constants.AEHubConstants;

public class AutherizationCallBack implements AEHAuthCallBack {

	private AutherizationParam param;

	public AutherizationCallBack(AutherizationParam param) {
		this.param = param;
	}

	@Override
	public void onSuccess() {

		HttpServletResponse response = param.getResponse();

		AutherizationResponse authenticationResponse = this.param.getAutherizationResponse();

		if (null == authenticationResponse) {

			response.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_HEADER, "false");
			response.setStatus(500);
			response.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_ERROR, "Couldnt complete autherization");
	
		}

		if (authenticationResponse.getResponse() == ResponseType.SUCCESS) {

			try {
				response.setHeader(AEHubConstants.ACCESS_TOKEN_HEADER,
						authenticationResponse.getBearerToken().toTokenString());
				response.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_HEADER, "true");

			} catch (AEHHubException e) {

				response.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_HEADER, "false");
				response.setStatus(500);
				response.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_ERROR, "Couldnt complete autherization");
			}

		}

	}

	@Override
	public void onFailure() {

		HttpServletResponse response = param.getResponse();

		AutherizationResponse authenticationResponse = this.param.getAutherizationResponse();

		if (null == authenticationResponse) {

			response.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_HEADER, "false");
			response.setStatus(500);
			response.setHeader(AEHubConstants.CLIENT_VALIDATION_RESPONSE_ERROR, "Couldnt complete autherization");
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
	public AutherizationParam getParam() {

		return this.param;
	}

}

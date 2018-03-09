package com.rohith.aeh.hub.authentication.dataobjects;

public class ResponseBase {

	private ResponseType response;
	
	private int responseCode;
	
	private String errorReason;

	public ResponseType getResponse() {
		return response;
	}

	public void setResponse(ResponseType response) {
		this.response = response;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getErrorReason() {
		return errorReason;
	}

	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	
	
	
}

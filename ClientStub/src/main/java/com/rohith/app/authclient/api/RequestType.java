package com.rohith.app.authclient.api;

public enum RequestType {

	ACCESS("ACCESS"), AUTHENTICATE("AUTHENTICATE"), AUTHERIZE("AUTHERIZE"), LOGOUT("LOGOUT"), INVALID("INVALID");

	private String requestType;

	private RequestType(String requestType) {

		this.requestType = requestType;
	}

	public String requestType() {

		return this.requestType;
	}

	/**
	 * <p>
	 * Static method for mapping request header to the corresponding
	 * 
	 * request type
	 * </p>
	 * 
	 * @param requestType
	 * @return
	 */
	public static RequestType resolve(String requestType) {

		if (null == requestType) {

			return RequestType.INVALID;
		}
		if (requestType.equals(RequestType.ACCESS.requestType)) {
			return RequestType.ACCESS;
		} else if (requestType.equals(RequestType.AUTHENTICATE.requestType)) {
			return RequestType.AUTHENTICATE;
		} else if (requestType.equals(RequestType.LOGOUT.requestType)) {
			return RequestType.LOGOUT;
		} else if (requestType.equals(RequestType.AUTHERIZE.requestType)) {
			return RequestType.AUTHERIZE;
		} else {
			return RequestType.INVALID;
		}

	}
}

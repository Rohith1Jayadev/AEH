package com.rohith.aeh.hub.util.param;

public class AEHHubPasswordValidator {

	private String regex;

	public AEHHubPasswordValidator() {

	}

	/**
	 * Validates Password based on policies
	 * 
	 * this is just a mock implements that check only for null and length > 8
	 * 
	 * @param password
	 * @return
	 */
	public boolean isValid(String password) {

		if (null == password || "".equals(password) || password.length() < 8) {

			return false;
		}

		return true;
	}

}

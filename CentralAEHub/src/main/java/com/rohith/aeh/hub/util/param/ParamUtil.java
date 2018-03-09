package com.rohith.aeh.hub.util.param;

public class ParamUtil {

	private static final AEHHubEmailValidator emailValidator = new AEHHubEmailValidator();

	private static final AEHHubPasswordValidator passwordValidator = new AEHHubPasswordValidator();

	public static boolean validateUserEmail(String emailAddress) {

		return emailValidator.isValid(emailAddress);

	}

	public static boolean validateSecurePasscode(String passcode) {

		return passwordValidator.isValid(passcode);

	}
}

package com.rohith.aeh.hub.util.param;

import org.apache.commons.validator.routines.EmailValidator;

public class AEHHubEmailValidator {

	private EmailValidator emailValidator;
	
	public AEHHubEmailValidator(){
		
		this.emailValidator = EmailValidator.getInstance(true);
	}
	
	public boolean isValid(String email){
		
		return this.emailValidator.isValid(email);
	}

}

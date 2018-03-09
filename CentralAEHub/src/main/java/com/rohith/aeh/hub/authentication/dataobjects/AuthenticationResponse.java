package com.rohith.aeh.hub.authentication.dataobjects;

public class AuthenticationResponse extends ResponseBase {
	
	private String authGrant;
	
	public String getAuthGrant() {
		return authGrant;
	}

	public void setAuthGrant(String authGrant) {
		this.authGrant = authGrant;
	}

}

package com.rohith.aeh.hub.util.token;

public class RefreshToken {

	private long expiry;
	
	private long secret;

	public long getExpiry() {
		return expiry;
	}

	public void setExpiry(long expiry) {
		this.expiry = expiry;
	}

	public long getSecret() {
		return secret;
	}

	public void setSecret(long secret) {
		this.secret = secret;
	}
	
	
}

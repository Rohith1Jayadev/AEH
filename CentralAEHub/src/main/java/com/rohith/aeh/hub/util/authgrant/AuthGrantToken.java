package com.rohith.aeh.hub.util.authgrant;

import com.rohith.aeh.hub.exception.AEHHubException;

public class AuthGrantToken {

	private String clientSecret;

	private String authorizer;

	private long expireTime;

	public String getClientSecret() {
		return clientSecret;
	}

	public AuthGrantToken setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
		return this;
	}

	public String getAuthorizer() {
		return authorizer;
	}

	public AuthGrantToken setAuthorizer(String authorizer) {
		this.authorizer = authorizer;
		return this;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public AuthGrantToken setExpireTime(long expireTime) {
		this.expireTime = expireTime;
		return this;
	}

	public static AuthGrantToken fromTokenString(String tokenString) throws AEHHubException {

		if (null == tokenString) {
			throw new AEHHubException("Token String recieved is invalid");
		} else {
			String[] split = tokenString.split("=");

			if (split.length < 3) {

				throw new AEHHubException("Toke String received is invalid");
			}

			AuthGrantToken token = new AuthGrantToken();

			token.setAuthorizer(split[0]);
			token.setClientSecret(split[1]);
			try {
				token.setExpireTime(Long.parseLong(split[2]));
			} catch (NumberFormatException e) {
				throw new AEHHubException("Toke String received is invalid", e);
			}
			return token;
		}

	}

	public String toTokenString() {

		return new StringBuilder().append(this.authorizer).append("=").append(this.clientSecret).append("=")
				.append(this.getExpireTime()).toString();

	}
}

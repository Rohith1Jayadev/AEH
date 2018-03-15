package com.rohith.aeh.hub.util.token;

import java.io.UnsupportedEncodingException;

import com.rohith.aeh.hub.authentication.dataobjects.UserProfile;
import com.rohith.aeh.hub.exception.AEHHubException;
import com.rohith.aeh.hub.servlets.constants.AccessGrantErrorCodes;
import com.rohith.aeh.hub.util.jwt.JWTUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class BearerToken {

	private String clientSecret;

	private String authorizingParty;

	private long expiryDate;

	private RefreshToken refreshToken;

	private UserProfile userProfile;

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getAuthorizingParty() {
		return authorizingParty;
	}

	public void setAuthorizingParty(String authorizingParty) {
		this.authorizingParty = authorizingParty;
	}

	public long getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(long expiryDate) {
		this.expiryDate = expiryDate;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public RefreshToken getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(RefreshToken refreshToken) {
		this.refreshToken = refreshToken;
	}

	public static BearerToken fromTokenString(String value) throws AEHHubException {

		try {
			return JWTUtil.parseToken(value);

		} catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException
				| UnsupportedEncodingException e) {
			throw new AEHHubException("Exception while parsing token", e);
		} catch (ExpiredJwtException e) {

			throw new AEHHubException("Exception while parsing token", e, AccessGrantErrorCodes.EXPIRED_TOKEN_KEY);
		}
	}

	public String toTokenString() throws AEHHubException {
		try {
			return JWTUtil.createToken(this);
		} catch (UnsupportedEncodingException e) {
			throw new AEHHubException("Exception while parsing token", e);
		}
	}

}

package com.rohith.aeh.hub.util.token;

import java.util.Optional;
import java.util.function.Predicate;

import com.rohith.aeh.hub.exception.AEHHubException;

/**
 * Utility Class that helps in creating a Bearer Token from String value and
 * vice versa
 * 
 * Other Utility Methods to perform operations on the bearer token
 * 
 * @author Accolite
 *
 */
public class TokenUtil {

	public static final int BEAER_TOKEN_EXPIRY_HOURS =2;
	
	/**
	 * Get Value from String
	 * 
	 * @param tokenString
	 * @return
	 * @throws AEHHubException 
	 */
	public static BearerToken getTokenValue(String tokenString) throws AEHHubException {
	
		return BearerToken.fromTokenString(tokenString);
	}

	/**
	 * Get String value from Bearer Token
	 * 
	 * @param token
	 * @return
	 * @throws AEHHubException 
	 */
	public static String getTokenString(BearerToken token) throws AEHHubException {
		return token.toTokenString();
	}

	/**
	 * Method helps in checking whether the requesting party has the scope
	 * 
	 * @param token
	 * @param requestedScope
	 * @return
	 */
	public static boolean hasScope(BearerToken token, Scope requestedScope) {
		if (null == requestedScope || null == token || null == token.getUserProfile().getScopes()
				|| token.getUserProfile().getScopes().size() == 0) {
			return false;
		}
		ScopeFilter filter = new ScopeFilter(requestedScope);
		Optional<Scope> findFirst = token.getUserProfile().getScopes().stream().filter(filter).findFirst();
		return findFirst.isPresent();
	}

	private static class ScopeFilter implements Predicate<Scope> {
		private Scope requestedScope;

		public ScopeFilter(Scope requestedScope) {
			this.requestedScope = requestedScope;
		}

		@Override
		public boolean test(Scope t) {
			return t.equals(requestedScope);
		}
	}

	/**
	 * 
	 * API for checking whether the token has expired or not
	 * 
	 * @param token
	 * @return
	 */
	public static boolean hasExpired(BearerToken token) {
		if (null == token || token.getExpiryDate() <= 0) {
			return false;
		}
		long currentDate = System.currentTimeMillis();
		if (token.getExpiryDate() >= currentDate) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * Bearer client secret validation
	 * 
	 * @param token
	 * @param requestedClientSecret
	 * @return
	 */
	public static boolean isClientValidated(BearerToken token, String requestedClientSecret) {

		if (null == token.getClientSecret() || null == requestedClientSecret || "".equals(requestedClientSecret)
				|| "".equals(token.getClientSecret())) {

			return false;
		}

		return requestedClientSecret.equals(token.getClientSecret());

	}
}

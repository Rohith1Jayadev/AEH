package com.rohith.aeh.hub.util.token;

import com.rohith.aeh.hub.util.date.AEHHubDateUtil;

public class RefreshTokenUtil {

	private static final String NULL_TOKEN = "0=0";

	private static final int REFRESH_TOKEN_EXPIRY_DAYS = 30;

	public static RefreshToken createRefreshToken(long expiryDate, long currentTime) {

		RefreshToken token = new RefreshToken();

		if (expiryDate <= 0) {
			return token;
		}

		long expiry = AEHHubDateUtil.addDays(currentTime, REFRESH_TOKEN_EXPIRY_DAYS);

		long secret = calculateSecret(expiry, expiryDate);

		token.setExpiry(expiry);
		token.setSecret(secret);

		return token;

	}

	/**
	 * Building the Refresh Token String from a refresh token object
	 * 
	 * <p>
	 * It just appends the Expiry date and the secret separated by a delimiter
	 * </p>
	 * 
	 * <p>
	 * If the object passed is null , it sends back a default format
	 * </p>
	 * 
	 * @param refreshToken
	 * @return
	 */
	public static String getRefreshToken(RefreshToken refreshToken) {

		if (null == refreshToken) {

			return NULL_TOKEN;
		}

		long expiry = refreshToken.getExpiry();

		long secret = refreshToken.getSecret();

		return (expiry + "=" + secret);
	}

	public static RefreshToken parseRefreshToken(String refreshToken) {

		RefreshToken refreshTokenObj = new RefreshToken();

		if (null != refreshToken || !"".equals(refreshToken) || !NULL_TOKEN.equals(refreshToken)) {

			String[] split = refreshToken.split("=");

			if (split.length == 2) {
				try {
					long expiry = Long.parseLong(split[0]);
					long secret = Long.parseLong(split[1]);
					refreshTokenObj.setExpiry(expiry);
					refreshTokenObj.setSecret(secret);
				} catch (Exception e) {
					System.out.println("Number format exception occured while parsing refresh token");
				}

			}

		}

		return refreshTokenObj;
	}

	/**
	 * Method calculates the secret based on Expiry date of the main token and
	 * refresh token
	 * 
	 * @param expiry
	 * @param expiryDate
	 * @return
	 */
	public static long calculateSecret(long expiryRefresh, long expiryMain) {

		return (expiryRefresh - expiryMain) * 31 - 121;

	}

	/**
	 * API for validating the refresh token secret passed
	 * 
	 * @param expiryRefresh
	 * @param expiryMain
	 * @param secret
	 * @return
	 */
	public static boolean isSecretValid(long expiryRefresh, long expiryMain, long secret) {
		long calcuatedSecret = calculateSecret(expiryRefresh, expiryMain);
		if (calcuatedSecret == secret) {
			return true;
		}
		return false;
	}

	/**
	 * API for checking whether refresh token has expired or not
	 * 
	 * @param refreshToken
	 * @return
	 */
	public static boolean hasExpired(RefreshToken refreshToken) {

		if (refreshToken.getExpiry() <= 0 || refreshToken.getExpiry() >= System.currentTimeMillis()) {

			return true;
		}

		return false;
	}

}

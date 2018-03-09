package com.rohith.aeh.hub.util.authgrant;

import javax.servlet.http.HttpServletRequest;

import com.rohith.aeh.hub.authentication.dataobjects.AEHAuthParamBase;
import com.rohith.aeh.hub.authentication.dataobjects.AuthenticationParam;
import com.rohith.aeh.hub.authentication.dataobjects.AutherizationParam;
import com.rohith.aeh.hub.servlets.constants.AEHubConstants;
import com.rohith.aeh.hub.util.date.AEHHubDateUtil;

/**
 * 
 * 
 * 
 * @author Accolite
 *
 */
public class AuthGrantUtil {

	public static boolean isValidClient(String clientSecret, AuthGrantToken token) {

		if (null == clientSecret || null == token.getClientSecret() || "".equals(clientSecret)
				|| "".equals(token.getClientSecret())) {
			return false;
		}
		return clientSecret.equals(token.getClientSecret());
	}

	public static boolean hasExpired(AuthGrantToken token) {

		if (token.getExpireTime() <= 0) {

			return false;
		}

		long currentTimeMillis = System.currentTimeMillis();

		return (token.getExpireTime() >= currentTimeMillis);
	}

	public static boolean isValidAuthorizer(String authorizer, AuthGrantToken token) {

		return true;
	}

	public static AuthGrantToken buildAuthGrant(AEHAuthParamBase param) {

		HttpServletRequest request = param.getRequest();

		AuthGrantToken grantToken = new AuthGrantToken();

		grantToken.setAuthorizer(request.getParameter(AEHubConstants.CLIENT_USER_NAME_PARAM))
				.setClientSecret(request.getHeader(AEHubConstants.CLIENT_SECRET_HEADER))
				.setExpireTime(AEHHubDateUtil.addMinutes(System.currentTimeMillis(), 10));

		return grantToken;
	}
}

package com.rohith.aeh.hub.validators;

import javax.servlet.http.HttpServletRequest;

import com.rohith.aeh.crypto.AEHCipher;
import com.rohith.aeh.crypto.exception.AEHCipherException;
import com.rohith.aeh.hub.exception.AEHHubException;
import com.rohith.aeh.hub.manager.AEHHubManager;
import com.rohith.aeh.hub.servlets.constants.AEHubConstants;
import com.rohith.aeh.hub.util.authgrant.AuthGrantToken;
import com.rohith.aeh.hub.util.authgrant.AuthGrantUtil;

public class AuthGrantValidator extends ClientSecretValidator {
	@Override
	public boolean validate(HttpServletRequest request) throws AEHHubException {
		boolean superValidation = super.validate(request);
		if (!superValidation) {
			return false;
		}
		return validateAuthGrant(request);
	}
	private boolean validateAuthGrant(HttpServletRequest request) {
		String authGrant = request.getHeader(AEHubConstants.AUTH_GRANT_HEADER);
		String clientSecret = request.getHeader(AEHubConstants.CLIENT_SECRET_HEADER);
		if (null == authGrant || "".equals(authGrant)) {
			return false;
		}
		AuthGrantToken token;
		try {
			String decryptedToken = decipher(authGrant);
			token = AuthGrantToken.fromTokenString(decryptedToken);
		} catch (AEHHubException e) {
			return false;
		}
		if (AuthGrantUtil.isValidClient(clientSecret, token) && AuthGrantUtil.hasExpired(token)) {
			return true;
		}
		return false;
	}
	private String decipher(String authGrant) throws AEHHubException  {
		AEHCipher ciper = AEHHubManager.getManager().getEncrytionManager().getCipher();
		try {
			return ciper.decipher(authGrant);
		} catch (AEHCipherException e) {
			throw new AEHHubException(e);
		}
	}

}

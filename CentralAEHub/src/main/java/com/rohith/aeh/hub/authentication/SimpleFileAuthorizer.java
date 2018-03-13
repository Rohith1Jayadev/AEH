package com.rohith.aeh.hub.authentication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import com.rohith.aeh.crypto.AEHCipher;
import com.rohith.aeh.crypto.exception.AEHCipherException;
import com.rohith.aeh.hub.authentication.dataobjects.AutherizationParam;
import com.rohith.aeh.hub.authentication.dataobjects.AutherizationResponse;
import com.rohith.aeh.hub.authentication.dataobjects.ResponseType;
import com.rohith.aeh.hub.authentication.dataobjects.UserProfile;
import com.rohith.aeh.hub.exception.AEHHubException;
import com.rohith.aeh.hub.manager.AEHHubManager;
import com.rohith.aeh.hub.servlets.constants.AEHubConstants;
import com.rohith.aeh.hub.util.authgrant.AuthGrantToken;
import com.rohith.aeh.hub.util.date.AEHHubDateUtil;
import com.rohith.aeh.hub.util.token.BearerToken;
import com.rohith.aeh.hub.util.token.Scope;
import com.rohith.aeh.hub.util.token.ScopeType;

public class SimpleFileAuthorizer extends RequestAutherizer {

	public Map<String, UserProfile> profileInfo;

	public SimpleFileAuthorizer() {
		this.profileInfo = new ConcurrentHashMap<String, UserProfile>();
		loadFromFileAndFillMap();
	}

	private void loadFromFileAndFillMap() {

		ClassLoader classLoader = getClass().getClassLoader();

		File file = new File(classLoader.getResource("profiles.txt").getFile());

		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				splitAndUpdate(line);
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}

	}

	private void splitAndUpdate(String line) {

		if (null != line) {
			String[] split = line.split("=");
			if (split.length > 1) {

				UserProfile profile = new UserProfile().setUserName(split[1]).setAddress(split[2])
						.setCompanyName(split[3]).setDesignation(split[4]);

				int scopeLength = 0;

				if ((split.length - 5) > 0) {

					scopeLength = (split.length - 5);
				}
				if (scopeLength > 0) {
					List<Scope> scopes = new ArrayList<Scope>();
					for (int i = 0; i < scopeLength; i++) {
						String scope = split[i + 5];
						String[] splitedScope = scope.split("#");
						Scope scopeal = getScope(splitedScope);
						scopes.add(scopeal);
					}

					profile.setScopes(scopes);
				}

				profileInfo.put(split[0], profile);
			}
		}

	}

	private Scope getScope(String[] splitedScope) {
		Scope scopeal = new Scope();
		scopeal.setScopeType(ScopeType.isEqual(splitedScope[0]));
		scopeal.setScopeValue(splitedScope[1]);
		return scopeal;
	}

	@Override
	public void autherize(AEHAuthCallBack callback) {

		HttpServletRequest request = callback.getParam().getRequest();

		String authGrant = request.getHeader(AEHubConstants.AUTH_GRANT_HEADER);
		try {
			
			String decipher = decipher(authGrant);
			AuthGrantToken token = AuthGrantToken.fromTokenString(decipher);

			if (null == token.getAuthorizer() || null == this.profileInfo.get(token.getAuthorizer())) {
				populateErrorResponse(callback, "User Profile Not Found");
				divert(callback, false);
			} else {

				createSuccessMessage(callback, request, token);
				divert(callback, true);
			}

		} catch (AEHHubException e) {
			populateErrorResponse(callback, "Invalid Auth Grant Received");
			divert(callback, false);
		}

	}

	private void createSuccessMessage(AEHAuthCallBack callback, HttpServletRequest request, AuthGrantToken token) {

		UserProfile profile = this.profileInfo.get(token.getAuthorizer());

		BearerToken bearerToken = new BearerToken();
		bearerToken.setExpiryDate(AEHHubDateUtil.addHours(System.currentTimeMillis(), 2));
		bearerToken.setUserProfile(profile);
		bearerToken.setClientSecret(request.getHeader(AEHubConstants.CLIENT_SECRET_HEADER));
		AutherizationResponse response = new AutherizationResponse();
		response.setBearerToken(bearerToken);
		response.setResponseCode(200);
		response.setResponse(ResponseType.SUCCESS);
		((AutherizationParam) callback.getParam()).setAutherizationResponse(response);
	}

	private void populateErrorResponse(AEHAuthCallBack callback, String message) {
		AutherizationResponse response = new AutherizationResponse();
		response.setResponseCode(400);
		response.setResponse(ResponseType.ERROR);
		response.setErrorReason(message);
		((AutherizationParam) callback.getParam()).setAutherizationResponse(response);
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

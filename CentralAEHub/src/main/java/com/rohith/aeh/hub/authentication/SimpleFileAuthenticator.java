package com.rohith.aeh.hub.authentication;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import com.rohith.aeh.crypto.AEHCipher;
import com.rohith.aeh.crypto.exception.AEHCipherException;
import com.rohith.aeh.hub.authentication.dataobjects.AuthenticationParam;
import com.rohith.aeh.hub.authentication.dataobjects.AuthenticationResponse;
import com.rohith.aeh.hub.authentication.dataobjects.ResponseType;
import com.rohith.aeh.hub.encryption.EncryptionHubManager;
import com.rohith.aeh.hub.manager.AEHHubManager;
import com.rohith.aeh.hub.servlets.constants.AEHubConstants;
import com.rohith.aeh.hub.util.authgrant.AuthGrantUtil;

public class SimpleFileAuthenticator extends RequestAuthenticator {

	public Map<String, String> authinfo;

	public SimpleFileAuthenticator() {

		this.authinfo = new ConcurrentHashMap<String, String>();

		loadFromFileAndFillMap();
	}

	private void loadFromFileAndFillMap() {

		ClassLoader classLoader = getClass().getClassLoader();

		File file = new File(classLoader.getResource("users.txt").getFile());

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
			if (split.length == 2) {
				authinfo.put(split[0], split[1]);
			}
		}

	}

	@Override
	public void authenticate(AEHAuthCallBack callBack) {
		HttpServletRequest request = callBack.getParam().getRequest();
		String userEmail = request.getParameter(AEHubConstants.CLIENT_USER_NAME_PARAM);
		String passcode = request.getParameter(AEHubConstants.CLIENT_SECURE_PASSWORD_PARAM);
		if (null == this.authinfo.get(userEmail) || !passcode.equals(this.authinfo.get(userEmail))) {
			populateErrorResponse(callBack, "Invalid User ID or Password.. User Not Found");
			divert(callBack, false);
		} else {
			try {
				populateSuccessAuthParam(callBack);
				divert(callBack, true);
			} catch (AEHCipherException e) {
				populateErrorResponse(callBack, "Error Occured while Ciphering Token Key");
				divert(callBack, false);
			}
		}
	}
	private void populateErrorResponse(AEHAuthCallBack callBack, String message) {
		AuthenticationResponse response = new AuthenticationResponse();
		response.setResponseCode(401);
		response.setResponse(ResponseType.ERROR);
		response.setErrorReason(message);
		((AuthenticationParam) callBack.getParam()).setAuthResponse(response);
	}
	private void populateSuccessAuthParam(AEHAuthCallBack callBack) throws AEHCipherException {
		AuthenticationParam param = (AuthenticationParam) callBack.getParam();
		AuthenticationResponse response = new AuthenticationResponse();
		response.setAuthGrant(createAuthGrant(AuthGrantUtil.buildAuthGrant(param).toTokenString()));
		response.setResponseCode(200);
		response.setResponse(ResponseType.SUCCESS);
		param.setAuthResponse(response);
	}

	private String createAuthGrant(String tokenString) throws AEHCipherException {
		EncryptionHubManager encrytionManager = AEHHubManager.getManager().getEncrytionManager();
		AEHCipher cipher = encrytionManager.getCipher();
		return cipher.cipher(tokenString);
	}
}

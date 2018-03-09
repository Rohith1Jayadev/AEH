package com.rohith.aeh.hub.authentication;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import com.rohith.aeh.hub.authentication.dataobjects.AuthenticationParam;
import com.rohith.aeh.hub.authentication.dataobjects.AuthenticationResponse;
import com.rohith.aeh.hub.authentication.dataobjects.ResponseType;
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
			
			populateErrorResponse(callBack);
			divert(callBack, false);
		
		} else {

			populateSuccessAuthParam(callBack);
			divert(callBack, true);
		}

	}

	private void populateErrorResponse(AEHAuthCallBack callBack) {
		AuthenticationResponse response = new AuthenticationResponse();
		response.setResponseCode(400);
		response.setResponse(ResponseType.ERROR);
		response.setErrorReason("Invalid User ID or Password.. User Not Found");
		((AuthenticationParam)callBack.getParam()).setAuthResponse(response);
		
	}

	private void populateSuccessAuthParam(AEHAuthCallBack callBack) {

		AuthenticationParam param = (AuthenticationParam) callBack.getParam();
		AuthenticationResponse response = new AuthenticationResponse();
		response.setAuthGrant(AuthGrantUtil.buildAuthGrant(param).toTokenString());
		response.setResponseCode(200);
		response.setResponse(ResponseType.SUCCESS);
		param.setAuthResponse(response);
	
	}

}

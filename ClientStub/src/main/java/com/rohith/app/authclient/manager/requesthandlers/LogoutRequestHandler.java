package com.rohith.app.authclient.manager.requesthandlers;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.rohith.app.authclient.api.AEHClientParam;
import com.rohith.app.authclient.api.RequestType;
import com.rohith.app.authclient.constants.AEHClientConstants;
import com.rohith.app.authclient.exception.AEHClientException;
import com.rohith.app.authclient.manager.AEHClientManager;

public class LogoutRequestHandler extends RequestHandlerBase {

	private RequestHandlerBase next;

	public LogoutRequestHandler(RequestType requestType, AEHClientManager manager) {
		super(requestType, manager);
		this.next = new InvalidRequestHandler(RequestType.INVALID, manager);
	}

	@Override
	public void handleRequest(AEHClientParam param) throws AEHClientException {

		if (!checkResponsibility(param.getRequestType())) {
			this.next.handleRequest(param);
		} else {
			try {
				handleLogOut(param);
			} catch (IOException e) {

				throw new AEHClientException("Error occured while handling the autherize request", e);
			}
		}

	}

	private void handleLogOut(AEHClientParam param) throws IOException {
		String bearerToken = getBearerToken(param.getRequest());
		if (null == bearerToken) {
			bearerToken = param.getRequest().getHeader(AEHClientConstants.BEARER_TOKEN);
		}
		if (null == bearerToken || "null".equals(bearerToken)) {
			sendErrorResponse(param, "User Not Logged In", 401);
		}

		deleteCookieAndSendResponse(param);
	}

	private void deleteCookieAndSendResponse(AEHClientParam param) throws IOException {
		Cookie cookie = getCookie(param.getRequest(), AEHClientConstants.BEARER_TOKEN);
		if (null != cookie) {
			cookie.setValue(null);
			param.getResponse().addCookie(cookie);
			sendResponse(param, "User Logged Out Successfully", 200);
		} else {
			sendErrorResponse(param, "User Not Logged In", 401);
		}

	}

}

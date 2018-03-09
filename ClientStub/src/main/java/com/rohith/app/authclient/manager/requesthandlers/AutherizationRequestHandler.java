package com.rohith.app.authclient.manager.requesthandlers;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.rohith.app.authclient.api.AEHClientParam;
import com.rohith.app.authclient.api.RequestType;
import com.rohith.app.authclient.config.AEHMasterConfig;
import com.rohith.app.authclient.constants.AEHClientConstants;
import com.rohith.app.authclient.exception.AEHClientException;
import com.rohith.app.authclient.manager.AEHClientManager;

public class AutherizationRequestHandler extends RequestHandlerBase {

	private RequestHandlerBase next;

	public AutherizationRequestHandler(RequestType requestType, AEHClientManager manager) {
		super(requestType, manager);
		this.next = new InvalidRequestHandler(RequestType.INVALID, manager);
	}

	@Override
	public void handleRequest(AEHClientParam param) throws AEHClientException {

		System.out.println("Autherrize Request Recieved");
		if (!checkResponsibility(param.getRequestType())) {
			this.next.handleRequest(param);
		}
		try {
			autherizeClient(param);
		} catch (IOException e) {

			throw new AEHClientException("Error occured while handling the autherize request", e);
		}
	}

	private void autherizeClient(AEHClientParam param) throws IOException {

		CloseableHttpResponse response = null;
		HttpGet getRequest = createRequest(param);
		response = sendGetRequest(getRequest);
		Header firstHeader = response.getFirstHeader(AEHClientConstants.SERVER_ACCESS_AUTH_RESPONSE);

		if (firstHeader.getValue().equals("true")) {

			sendResponseAddCookie(param, response);

		} else {

			System.out.println("Autherization not successful");
		}
	}

	private void sendResponseAddCookie(AEHClientParam param, CloseableHttpResponse response) throws IOException {
		HttpServletRequest request = param.getRequest();
		HttpServletResponse httpServletResponse = param.getResponse();
		String accessPageUrl = request.getParameter("passUrl");

		String accessToken = response.getFirstHeader(AEHClientConstants.ACCESS_TOKEN_HEADER).getValue();
		httpServletResponse.addHeader(AEHClientConstants.ACCESS_TOKEN_HEADER, accessToken);

		// ttpServletResponse.addCookie(createCookie(accessToken));

	}

	private Cookie createCookie(String accessToken) {
		Cookie c = new Cookie(AEHClientConstants.BEARER_TOKEN, accessToken);
		return c;
	}

	private HttpGet createRequest(AEHClientParam param) throws IOException {

		HttpGet getRequest = new HttpGet(buildMasterUrl());
		getRequest.addHeader(AEHClientConstants.CLIENT_SECRET_HEADER, param.getClientSecret());
		getRequest.addHeader(AEHClientConstants.CLIENT_NAME_HEADER, getManager().getMasterConfig().getClientSecret());
		String authGrant = param.getResponse().getHeader(AEHClientConstants.AUTH_GRANT_HEADER);
		if (null == authGrant) {
			sendErrorResponse(param,
					"Auth Grant Not received from the server, hence redirecting response back to user");
		}
		getRequest.addHeader(AEHClientConstants.AUTH_GRANT_HEADER, authGrant);
		return getRequest;
	}

	private String buildMasterUrl() {
		AEHMasterConfig config = getManager().getMasterConfig();
		StringBuilder url = new StringBuilder(AEHClientConstants.DEFAULT_SCHEME);
		url.append(config.getMasterHost()).append(":").append(config.getMasterPort())
				.append(config.getMasterAutherizatonUrl());
		return url.toString();
	}

}

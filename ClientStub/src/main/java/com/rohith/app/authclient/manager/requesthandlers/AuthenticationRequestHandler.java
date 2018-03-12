package com.rohith.app.authclient.manager.requesthandlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ResponseAuthCache;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.rohith.app.authclient.api.AEHClientParam;
import com.rohith.app.authclient.api.RequestType;
import com.rohith.app.authclient.config.AEHMasterConfig;
import com.rohith.app.authclient.constants.AEHClientConstants;
import com.rohith.app.authclient.exception.AEHClientException;
import com.rohith.app.authclient.manager.AEHClientManager;

public class AuthenticationRequestHandler extends RequestHandlerBase {

	private RequestHandlerBase next;
	public AuthenticationRequestHandler(RequestType requestType, AEHClientManager manager) {
		super(requestType, manager);
		this.next = new AutherizationRequestHandler(RequestType.AUTHERIZE, manager);
	}
	@Override
	public void handleRequest(AEHClientParam param) throws AEHClientException {
		if (!checkResponsibility(param.getRequestType())) {
			this.next.handleRequest(param);
		} else {
			authenticateClient(param);
		}
	}
	private void authenticateClient(AEHClientParam param) throws AEHClientException {
		CloseableHttpResponse response = null;
		try {
			HttpPost postRequest = createRequest(param);
			response = sendPostRequest(postRequest);
			Header firstHeader = response.getFirstHeader(AEHClientConstants.SERVER_ACCESS_AUTH_RESPONSE);
			if (firstHeader.getValue().equals("true")) {
				Header authGrant = response.getFirstHeader(AEHClientConstants.AUTH_GRANT_HEADER);
				if (null == authGrant || "".equals(authGrant.getValue())) {
					sendErrorResponse(param, "Server didnt respond", 503);
				} else {
					HttpServletResponse httpServletResponse = param.getResponse();
					httpServletResponse.setHeader(AEHClientConstants.AUTH_GRANT_HEADER, authGrant.getValue());
					param.setRequestType(RequestType.AUTHERIZE);
					next.handleRequest(param);
				}
			} else {
				sendErrorResponse(param, "Not Sucessfully Authenticated", 401);
			}
		} catch (ClientProtocolException e) {
			throw new AEHClientException("A client protocol exception occured while communicating with host", e);
		} catch (IOException e) {
			throw new AEHClientException("An IO Exception  occured while communicating with host", e);
		} finally {
			try {
				if (null != response) {
					response.close();
				}
			} catch (IOException e) {
				throw new AEHClientException("An IO Exception  occured while closing the response from server", e);
			}
		}
	}
	private HttpPost createRequest(AEHClientParam param) throws UnsupportedEncodingException {
		AEHMasterConfig config = getManager().getMasterConfig();
		HttpServletRequest request = param.getRequest();
		HttpPost post = new HttpPost(buildMasterUrl(param.getRequest(), config));
		post.addHeader(AEHClientConstants.CLIENT_SECRET_HEADER, param.getClientSecret());
		post.addHeader(AEHClientConstants.CLIENT_NAME_HEADER, getManager().getMasterConfig().getClientSecret());
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(AEHClientConstants.CLIENT_USER_NAME_PARAM, request.getParameter("email")));
		params.add(new BasicNameValuePair(AEHClientConstants.CLIENT_SECURE_PASSWORD_PARAM,
				request.getParameter("password")));
		post.setEntity(new UrlEncodedFormEntity(params, HTTP.DEF_CONTENT_CHARSET));
		return post;
	}

	private String buildMasterUrl(HttpServletRequest httpServletRequest, AEHMasterConfig config) {

		StringBuilder builder = new StringBuilder(AEHClientConstants.DEFAULT_SCHEME).append(config.getMasterHost())
				.append(":").append(config.getMasterPort()).append(config.getMasterAuthenticationUrl());
		return builder.toString();
	}

}

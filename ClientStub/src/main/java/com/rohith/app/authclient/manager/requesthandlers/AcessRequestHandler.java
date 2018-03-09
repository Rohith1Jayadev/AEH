package com.rohith.app.authclient.manager.requesthandlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.rohith.app.authclient.api.AEHClientParam;
import com.rohith.app.authclient.api.RequestType;
import com.rohith.app.authclient.config.AEHMasterConfig;
import com.rohith.app.authclient.constants.AEHClientConstants;
import com.rohith.app.authclient.exception.AEHClientException;
import com.rohith.app.authclient.manager.AEHClientManager;

public class AcessRequestHandler extends RequestHandlerBase {

	private RequestHandlerBase next;

	public AcessRequestHandler(RequestType requestType, AEHClientManager manager) {
		super(requestType, manager);
		this.next = new AuthenticationRequestHandler(RequestType.AUTHENTICATE, manager);
	}

	@Override
	public void handleRequest(AEHClientParam param) throws AEHClientException {
		if (!checkResponsibility(param.getRequestType())) {
			this.next.handleRequest(param);
		} else {
			handleAccessRequestParam(param);
		}
	}

	private void handleAccessRequestParam(AEHClientParam param) throws AEHClientException {

		CloseableHttpResponse response = null;
		try {
			HttpGet getRequest = createRequest(param);
			response = sendGetRequest(getRequest);
			Header firstHeader = response.getFirstHeader(AEHClientConstants.SERVER_ACCESS_AUTH_RESPONSE);
			if (null != firstHeader && firstHeader.getValue().equals("true")) {
				String bearerToken = getBearerToken(param.getRequest());
				if (null == bearerToken) {
					bearerToken = getRequest.getFirstHeader(AEHClientConstants.BEARER_TOKEN).getValue();
				}
				param.getResponse().addCookie(new Cookie(AEHClientConstants.BEARER_TOKEN, bearerToken));
				param.getChain().doFilter(param.getRequest(), param.getResponse());
			} else {
				createRedirectResponse(param, response.getFirstHeader(AEHClientConstants.SERVER_REDIRECT_URL));
			}
		} catch (ClientProtocolException e) {
			throw new AEHClientException("A client protocol exception occured while communicating with host", e);
		} catch (IOException e) {
			throw new AEHClientException("An IO Exception  occured while communicating with host", e);
		} catch (ServletException e) {
			throw new AEHClientException("Can't pass the request forward to the filter chain", e);
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

	private void createRedirectResponse(AEHClientParam param, Header header) throws IOException, AEHClientException {

		if (null == header) {
			throw new AEHClientException("REDIRECT URL not recieved from Server");
		}
		String redirectURL = header.getValue();
		HttpServletResponse response = param.getResponse();
		HttpServletRequest request = param.getRequest();
		String requestURL = buildURL(request);
		String initURL = request.getHeader("referer");
		String enhancedURL = enhanceURL(redirectURL, requestURL, initURL);
		response.setHeader(AEHClientConstants.CLIENT_REDIRECT_STATUS, "true");
		response.setStatus(200);
		response.setHeader(AEHClientConstants.CLIENT_REDIRECT_URL, enhancedURL);
		response.flushBuffer();
	}

	private String enhanceURL(String base, String addOn, String initURL) {
		return base + "?accessUrl=" + addOn + "&initUrl=" + initURL;
	}

	private String buildURL(HttpServletRequest request) {
		StringBuilder builder = new StringBuilder(request.getScheme() + "://");
		builder.append(request.getServerName()).append(":").append(request.getServerPort())
				.append(request.getContextPath()).append("/").append("OAuth/2/");
		return builder.toString();
	}

	private HttpGet createRequest(AEHClientParam param) {
		HttpServletRequest request = param.getRequest();
		HttpGet getRequest = new HttpGet(buildMasterUrl());
		getRequest.addHeader(AEHClientConstants.CLIENT_SECRET_HEADER, param.getClientSecret());
		getRequest.addHeader(AEHClientConstants.CLIENT_NAME_HEADER, getManager().getMasterConfig().getClientSecret());
		String accessToken = getBearerToken(request);
		if (null == accessToken) {
			accessToken = request.getHeader(AEHClientConstants.BEARER_TOKEN);
		}
		getRequest.addHeader(AEHClientConstants.BEARER_TOKEN, accessToken);
		getRequest.addHeader(AEHClientConstants.REQUEST_SCOPE, request.getContextPath() + request.getServletPath());
		getRequest.addHeader(AEHClientConstants.REQUEST_SCOPE_TYPE, request.getMethod());
		return getRequest;
	}

	private String getBearerToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (null == cookies || cookies.length == 0) {
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			if (AEHClientConstants.BEARER_TOKEN.equals(cookies[i].getName())) {
				return cookies[i].getValue();
			}
		}
		return null;
	}

	private String buildMasterUrl() {
		AEHMasterConfig config = getManager().getMasterConfig();
		StringBuilder url = new StringBuilder("http://");
		url.append(config.getMasterHost()).append(":").append(config.getMasterPort()).append(config.getMasterUrl());
		return url.toString();
	}
}

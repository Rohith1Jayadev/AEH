package com.rohith.app.authclient.manager.requesthandlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.rohith.app.authclient.api.AEHClientParam;
import com.rohith.app.authclient.api.RequestType;
import com.rohith.app.authclient.config.AEHMasterConfig;
import com.rohith.app.authclient.constants.AEHClientConstants;
import com.rohith.app.authclient.constants.ServerErrorCodes;
import com.rohith.app.authclient.exception.AEHClientException;
import com.rohith.app.authclient.manager.AEHClientManager;
import com.rohith.app.authclient.util.ValidationUtil;

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

	/**
	 * Method Handles the ACCESS type request coming in for any client
	 * 
	 * @param param
	 * @throws AEHClientException
	 */
	private void handleAccessRequestParam(AEHClientParam param) throws AEHClientException {

		CloseableHttpResponse response = null;
		try {
			HttpGet getRequest = createRequest(param);
			response = sendGetRequest(getRequest);
			handleResponse(param, response, getRequest);
		} catch (Exception e) {
			throw new AEHClientException("An exception occured while communicating with host", e);
		} finally {
			try {
				closeResponse(response);
			} catch (IOException e) {
				throw new AEHClientException("Exception Occured while closing the response", e);
			}
		}
	}

	/**
	 * 
	 * Method handles the response sent from the AEH HUB Server
	 * <p>
	 * Splits the response in to two channels SUCCESS and ERROR based on a
	 * <b><ul>SERVER AUTH RESPONSE HEADER</ul></b>
	 * 
	 * This header is mandatory from the Server Side as a decision is made based
	 * on it.
	 * </p>
	 * 
	 * @param param
	 * @param response
	 * @param getRequest
	 * @throws IOException
	 * @throws ServletException
	 * @throws AEHClientException
	 */
	private void handleResponse(AEHClientParam param, CloseableHttpResponse response, HttpGet getRequest)
			throws IOException, ServletException, AEHClientException {
		Header firstHeader = response.getFirstHeader(AEHClientConstants.SERVER_ACCESS_AUTH_RESPONSE);
		if (null != firstHeader && firstHeader.getValue().equals("true")) {
			onSuccessAccessGrant(param, getRequest);
		} else {
			onFailedAccessGrant(param, response, getRequest);
		}
	}

	/**
	 * Method handles the Failed Access Response 
	 * 
	 * <p> This method handles the failed response as in the responses that couldn't be validated 
	 * 
	 * properly by the AEHHub Server</p> 
	 * 
	 * <p> This could be due to different reason such as an invalid Bearer Token or Invalid Scope or Invalid Client Secret </p> 
	 * 
	 * 
	 * @param param
	 * @param response
	 * @param getRequest
	 * @throws IOException
	 * @throws AEHClientException
	 */
	private void onFailedAccessGrant(AEHClientParam param, CloseableHttpResponse response, HttpGet getRequest)
			throws IOException, AEHClientException {

		Header firstHeader = response.getFirstHeader(AEHClientConstants.ERROR_CODE_MAPPING);

		System.out.println(firstHeader);
		
		if (ValidationUtil.isHeaderNull(firstHeader)) {

			createRedirectResponse(param, response.getFirstHeader(AEHClientConstants.SERVER_REDIRECT_URL));

		} else {

			try {
			
				int errorCode = Integer.parseInt(firstHeader.getValue());
			
				switch(errorCode){
				case ServerErrorCodes.EXPIRED_TOKEN_KEY:
					sendErrorResponse(param, "Token Key Expired", 403); 
					break;
				case ServerErrorCodes.NULL_TOKEN_KEY:
				case ServerErrorCodes.INVALID_TOKEN_KEY:
					createRedirectResponse(param, response.getFirstHeader(AEHClientConstants.SERVER_REDIRECT_URL));
					break;
				case ServerErrorCodes.SCOPE_NOT_GRANTED:
				case ServerErrorCodes.INVALID_SCOPE:
						sendErrorResponse(param, "Contained is Restricted for User", 403); 
					break;
				case ServerErrorCodes.INVALID_CLIENT_SECRET:
					break;
				}

			} catch (NumberFormatException e) {

				createRedirectResponse(param, response.getFirstHeader(AEHClientConstants.SERVER_REDIRECT_URL));

			}

		}

	}

	/**
	 * Method helps in adding cookie back to the
	 * 
	 * @param param
	 * @param getRequest
	 * @throws IOException
	 * @throws ServletException
	 */
	private void onSuccessAccessGrant(AEHClientParam param, HttpGet getRequest) throws IOException, ServletException {
	
		String bearerToken = getBearerToken(param.getRequest());
		if (!ValidationUtil.isValid(bearerToken)) {
			bearerToken = getRequest.getFirstHeader(AEHClientConstants.BEARER_TOKEN).getValue();
			param.getResponse().addCookie(addCookie(AEHClientConstants.BEARER_TOKEN, bearerToken, 30));
		}
		param.getChain().doFilter(param.getRequest(), param.getResponse());
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
		if (null == accessToken || "".equals(accessToken) || "null".equals(accessToken)) {
			accessToken = request.getHeader(AEHClientConstants.BEARER_TOKEN);
		}
		getRequest.addHeader(AEHClientConstants.BEARER_TOKEN, accessToken);
		getRequest.addHeader(AEHClientConstants.REQUEST_SCOPE, request.getContextPath() + request.getServletPath());
		getRequest.addHeader(AEHClientConstants.REQUEST_SCOPE_TYPE, request.getMethod());
		return getRequest;
	}

	private String buildMasterUrl() {
		AEHMasterConfig config = getManager().getMasterConfig();
		StringBuilder url = new StringBuilder(AEHClientConstants.DEFAULT_SCHEME);
		url.append(config.getMasterHost()).append(":").append(config.getMasterPort()).append(config.getMasterUrl());
		return url.toString();
	}
}

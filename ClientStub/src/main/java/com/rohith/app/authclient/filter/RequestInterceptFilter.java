package com.rohith.app.authclient.filter;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
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
import com.rohith.app.authclient.manager.connection.ConnectionManager;

@WebFilter("/OAuth/*")
public class RequestInterceptFilter implements Filter {
	private AEHClientManager clientManager;
	private String clientSecret;
	private AtomicBoolean isRegistered;

	public void destroy() {
		clientManager.destroy();
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		if (null != this.clientManager) {
			try {

				checkWhetherRegistered();

				clientManager.client()
						.onRequest(createAuthParam((HttpServletRequest) req, (HttpServletResponse) res, chain));
			} catch (AEHClientException e) {
				throw new ServletException("AEHClient threw a client exception while handling request", e);
			}
		} else {
			throw new ServletException("The Auth Client is not configured");
		}
	}

	private void checkWhetherRegistered() throws ClientProtocolException, IOException, AEHClientException {
		
		if (!isRegistered.get()) {

			synchronized (this) {

				if (!isRegistered.get()) {

					registerToServer();
				}

			}
		}
	}

	private AEHClientParam createAuthParam(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
		return new AEHClientParam().setRequest(req).setResponse(res).setChain(chain)
				.setRequestType(resolveClientRequestType(req)).setClientSecret(this.clientSecret);
	}

	private RequestType resolveClientRequestType(HttpServletRequest req) {
		RequestType type = clientManager.client()
				.resolveRequestType(req.getHeader(AEHClientConstants.CLIENT_ACCESS_TYPE_HEADER));
		System.out.println(type.requestType());

		return type;

	}

	public void init(FilterConfig config) throws ServletException {
	
	try {
			clientManager = AEHClientManager.getManager();
			this.isRegistered = new AtomicBoolean(false);
		} catch (Exception e) {
			throw new ServletException("Error occured while initializing intercept filter", e);
		}
	}

	private void registerToServer() throws ClientProtocolException, IOException, AEHClientException {

		HttpGet request = new HttpGet(buildMasterURL());
		request.addHeader(AEHClientConstants.CLIENT_NAME_HEADER, clientManager.getMasterConfig().getClientSecret());
		ConnectionManager connectionManager = this.clientManager.getConnectionManager();
		CloseableHttpResponse response = connectionManager.executeGetRequest(request,
				connectionManager.getClientContext());
		Header firstHeader = response.getFirstHeader(AEHClientConstants.CLIENT_REGISTRATION_STATUS_HEADER);
		if (null == firstHeader || firstHeader.getValue().equals(AEHClientConstants.REGISTRATION_FAILED)) {
			throw new AEHClientException("Registration with server failed");
		} else {
			Header secret = response.getFirstHeader(AEHClientConstants.CLIENT_SECRET_HEADER);
			if (null == secret || "".equals(secret.getValue())) {
				throw new AEHClientException("Registration with server failed");
			} else {
				this.clientSecret = secret.getValue();
				this.isRegistered.set(true);
			}
		}
	}

	private String buildMasterURL() {
		AEHMasterConfig config = clientManager.getMasterConfig();
		StringBuilder url = new StringBuilder("http://");
		url.append(config.getMasterHost()).append(":").append(config.getMasterPort())
				.append(config.getMasterRegisterUrl());
		return url.toString();
	}
}

package com.rohith.app.authclient.api;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AEHClientParam {

	private RequestType requstSource;
	
	private HttpServletRequest request;
	
	private  HttpServletResponse response;

	private FilterChain chain;
	
	private String clientSecret;

	public String getClientSecret() {
		return clientSecret;
	}
	public AEHClientParam setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
		return this;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public AEHClientParam setRequest(HttpServletRequest request) {
		this.request = request;
		return this;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public AEHClientParam setResponse(HttpServletResponse response) {
		this.response = response;
		return this;
	}
	public FilterChain getChain() {
		return chain;
	}
	public AEHClientParam setChain(FilterChain chain) {
		this.chain = chain;
		return this;
	}
	public RequestType getRequestType() {
		return requstSource;
	}

	public AEHClientParam setRequestType(RequestType requstSource) {
		this.requstSource = requstSource;
		return this;
	}

	
}

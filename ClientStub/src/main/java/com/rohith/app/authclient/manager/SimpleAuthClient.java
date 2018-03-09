package com.rohith.app.authclient.manager;

import com.rohith.app.authclient.api.AEHClientParam;
import com.rohith.app.authclient.api.RequestType;
import com.rohith.app.authclient.exception.AEHClientException;
import com.rohith.app.authclient.manager.requesthandlers.AcessRequestHandler;
import com.rohith.app.authclient.manager.requesthandlers.RequestHandlerBase;

public class SimpleAuthClient implements AuthClient {

	private AEHClientManager manager;

	private RequestHandlerBase requestHandler;

	public SimpleAuthClient(AEHClientManager clientManager) {

		this.manager = clientManager;

		this.requestHandler = new AcessRequestHandler(RequestType.ACCESS, this.manager);
	}

	public void onRequest(AEHClientParam param) throws AEHClientException {

		if (null == this.requestHandler) {

			throw new AEHClientException("No Request Hander Configured");
		}

		this.requestHandler.handleRequest(param);

	}

	public RequestType resolveRequestType(String requestHeader) {

		return RequestType.resolve(requestHeader);
	}
}

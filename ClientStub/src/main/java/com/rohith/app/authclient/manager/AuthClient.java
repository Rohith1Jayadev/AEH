package com.rohith.app.authclient.manager;

import com.rohith.app.authclient.api.AEHClientParam;
import com.rohith.app.authclient.api.RequestType;
import com.rohith.app.authclient.exception.AEHClientException;

public interface AuthClient {

	public void onRequest(AEHClientParam clientParam) throws  AEHClientException;
	
	public RequestType resolveRequestType(String requestHeader);
}

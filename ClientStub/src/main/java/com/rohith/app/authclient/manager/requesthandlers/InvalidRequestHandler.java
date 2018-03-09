package com.rohith.app.authclient.manager.requesthandlers;

import com.rohith.app.authclient.api.AEHClientParam;
import com.rohith.app.authclient.api.RequestType;
import com.rohith.app.authclient.manager.AEHClientManager;

public class InvalidRequestHandler extends RequestHandlerBase {

	private RequestHandlerBase next;
		
	public InvalidRequestHandler(RequestType requestType, AEHClientManager manager) {
		super(requestType, manager);
	    this.next = null;
	}

	@Override
	public void handleRequest(AEHClientParam param) {
		
		
		//There is no next in the case of the invalid request handler 
		
		//Last in chain
		
	}

}

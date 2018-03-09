package com.rohith.aeh.hub.authentication;

import com.rohith.aeh.hub.authentication.dataobjects.AutherizationParam;
import com.rohith.aeh.hub.authentication.dataobjects.AutherizationResponse;

public class GoogleAPIAutherizer extends RequestAutherizer{

	
	
	public void autherize(AEHAuthCallBack callback) {
		
		AutherizationResponse response = new AutherizationResponse();
		
		divert(callback, true);
	}

	private boolean getAutherizedExternal(AutherizationParam param) {
		
		
	
		
		return false;
	}

	
		
	
	
}

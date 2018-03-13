package com.rohith.aeh.hub.authentication;

import com.rohith.aeh.hub.authentication.dataobjects.AEHAuthParamBase;

public interface AEHAuthCallBack {
		
	public void onSuccess();
	
	public void onFailure();
	
	public AEHAuthParamBase getParam();
}

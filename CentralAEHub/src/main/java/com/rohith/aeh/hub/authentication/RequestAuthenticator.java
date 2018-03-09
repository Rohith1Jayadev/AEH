package com.rohith.aeh.hub.authentication;

public abstract class RequestAuthenticator {

	
	protected void divert(AEHAuthCallBack callBack , boolean result){
	
		if (result) {
				callBack.onSuccess();
			} else {
				callBack.onFailure();
			}	
		
	}
	
	
	

	public abstract void authenticate(AEHAuthCallBack callBack);
	
	
}

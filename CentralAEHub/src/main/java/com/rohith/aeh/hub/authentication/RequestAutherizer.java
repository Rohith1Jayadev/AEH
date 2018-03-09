package com.rohith.aeh.hub.authentication;

public abstract class RequestAutherizer {

	public void divert(AEHAuthCallBack callBack, boolean result) {

		if (result) {
			callBack.onSuccess();
		} else {
			callBack.onFailure();
		}

	}

	public abstract void autherize(AEHAuthCallBack callback);

}

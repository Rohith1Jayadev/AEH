package com.rohith.aeh.hub.authentication.dataobjects;

import com.rohith.aeh.hub.util.token.BearerToken;

public class AutherizationResponse extends ResponseBase{

		private BearerToken bearerToken;

		public BearerToken getBearerToken() {
			return bearerToken;
		}

		public void setBearerToken(BearerToken bearerToken) {
			this.bearerToken = bearerToken;
		}
		
		
	
}

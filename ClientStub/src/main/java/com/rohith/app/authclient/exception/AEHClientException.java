package com.rohith.app.authclient.exception;

public class AEHClientException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public AEHClientException(String message, Throwable cause){
		
		super(message, cause);
	}

	public AEHClientException(String message){
		
		super(message);
	}
	
	public AEHClientException(Throwable cause){
		
		super(cause);
	}
}

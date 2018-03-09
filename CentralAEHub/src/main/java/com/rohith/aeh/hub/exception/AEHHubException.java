package com.rohith.aeh.hub.exception;

public class AEHHubException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AEHHubException(String message, Throwable cause){
		
		super(message, cause);
	}
	
	public AEHHubException(String message){
		
		super(message);
	}
	
	public AEHHubException(Throwable cause){
		
		super(cause);
	}

}

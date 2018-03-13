package com.rohith.aeh.crypto.exception;

public class AEHCipherException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public AEHCipherException(String message){
		
		super(message);
	}
	
	public AEHCipherException(String message, Throwable cause){
		
		super(message,cause);
	}
	
	public AEHCipherException(Throwable cause){
		
		super(cause);
	}

}

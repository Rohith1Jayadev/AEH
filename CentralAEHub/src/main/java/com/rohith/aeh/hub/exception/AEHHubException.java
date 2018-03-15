package com.rohith.aeh.hub.exception;

public class AEHHubException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int type = -1;
	
	public AEHHubException(String message, Throwable cause){
		
		super(message, cause);
	}
	
	public AEHHubException(String message, Throwable cause, int type){
		
		super(message, cause);
		this.type=type;
	}
	
	public AEHHubException(String message){
		
		super(message);
	}
	
	public AEHHubException(Throwable cause){
		
		super(cause);
	}
	
	public int getType(){
		
		return this.type;
	}

}

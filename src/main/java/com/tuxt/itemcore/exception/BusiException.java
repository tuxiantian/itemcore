package com.tuxt.itemcore.exception;
public class BusiException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 260306311701422290L;
	private String exceptionId;
	
	public String getExceptionId() {
		return exceptionId;
	}
	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}
	public BusiException(String message) {
		super(message);
	}
	public BusiException(Throwable cause) {
		super(cause);
		this.setStackTrace(cause.getStackTrace());
	}
	public BusiException(String message,Throwable cause) {
		super(message,cause);
		this.setStackTrace(cause.getStackTrace());
	}
	public BusiException(String exceptionId,String message) {
		super(message);
		this.exceptionId=exceptionId;
	}
	public BusiException(String exceptionId,String message,Throwable cause) {
		super(message,cause);
		this.exceptionId=exceptionId;
		this.setStackTrace(cause.getStackTrace());
	}
}
package ug.or.nda.exceptions;

public class WhitelistingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4498725891601503173L;
	public String message;
	public Throwable throwable;
	
	
	public WhitelistingException(String message){
		this.message = message;
	}
	public WhitelistingException(String message, Throwable throwable){
		this.message = message;
		this.throwable = throwable;
	}
	public Throwable getThrowable() {
		return throwable;
	}
	public String getMessage() {
		return message;
	}

}

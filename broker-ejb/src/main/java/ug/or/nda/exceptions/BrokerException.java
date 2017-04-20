package ug.or.nda.exceptions;

public class BrokerException extends Exception {

	public String message;
	public Throwable throwable;
	
	private static final long serialVersionUID = 6321359969013259805L;
	
	
	public BrokerException(String message){
		this.message = message;
	}
	public BrokerException(String message, Throwable throwable){
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

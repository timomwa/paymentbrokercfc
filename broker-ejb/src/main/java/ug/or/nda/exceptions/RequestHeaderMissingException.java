package ug.or.nda.exceptions;

public class RequestHeaderMissingException extends BrokerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2038582117536256027L;

	public RequestHeaderMissingException(String message) {
		super(message);
	}
	
	public RequestHeaderMissingException(String message, Throwable throwable){
		super(message, throwable);
	}
	

}

package ug.or.nda.exceptions;

public class InvalidInvoiceException extends BrokerException {

	public InvalidInvoiceException(String message) {
		super(message);
	}
	
	public InvalidInvoiceException(String message, Throwable throwable){
		super(message, throwable);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6321359969013259805L;

}

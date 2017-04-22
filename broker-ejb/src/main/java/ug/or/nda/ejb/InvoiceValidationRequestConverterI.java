package ug.or.nda.ejb;

import ug.or.nda.dto.InvoiceValidationRequestDTO;
import ug.or.nda.dto.InvoiceValidationResponseDTO;
import ug.or.nda.exceptions.BrokerException;
import ug.or.nda.ws.InvoiceValidationRequest;
import ug.or.nda.ws.InvoiceValidationResponse;

public interface InvoiceValidationRequestConverterI {
	
	public InvoiceValidationRequest convert(InvoiceValidationRequestDTO paymentNotification) throws BrokerException;

	public InvoiceValidationResponseDTO convert(InvoiceValidationResponse resp, boolean paymentExists, String systemMessage) throws BrokerException;

}

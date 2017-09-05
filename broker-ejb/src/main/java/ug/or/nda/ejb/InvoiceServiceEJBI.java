package ug.or.nda.ejb;


import ug.or.nda.dto.InvoiceDTO;
import ug.or.nda.dto.InvoiceValidationRequestDTO;
import ug.or.nda.dto.InvoiceValidationResponseDTO;
import ug.or.nda.entities.InvoiceValidationRawLog;
import ug.or.nda.exceptions.BrokerException;

public interface InvoiceServiceEJBI {
	
	public InvoiceValidationResponseDTO  validateInvoice(InvoiceValidationRequestDTO invoiceValidationReq, String ipAddress)  throws BrokerException;

	public InvoiceDTO findInvoiceByInvoiceNumber(String invoiceNo)  throws BrokerException;
	
	public InvoiceValidationRawLog save(InvoiceValidationRawLog notificationRawLog) throws Exception;

}

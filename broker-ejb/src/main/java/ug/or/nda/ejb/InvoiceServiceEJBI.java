package ug.or.nda.ejb;


import java.util.List;

import ug.or.nda.dto.InvoiceDTO;
import ug.or.nda.dto.InvoiceValidationRequestDTO;
import ug.or.nda.dto.InvoiceValidationResponseDTO;
import ug.or.nda.dto.QueryDTO;
import ug.or.nda.entities.Invoice;
import ug.or.nda.exceptions.BrokerException;

public interface InvoiceServiceEJBI {
	
	public InvoiceValidationResponseDTO  validateInvoice(InvoiceValidationRequestDTO invoiceValidationReq)  throws BrokerException;

	public InvoiceDTO findInvoiceByInvoiceNumber(String invoiceNo)  throws BrokerException;

}

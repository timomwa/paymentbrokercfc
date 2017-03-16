package ug.or.nda.ejb;


import ug.or.nda.dto.InvoiceDTO;
import ug.or.nda.dto.InvoiceValidationRequestDTO;
import ug.or.nda.dto.InvoiceValidationResponseDTO;

public interface InvoiceServiceEJBI {
	
	public InvoiceValidationResponseDTO  validateInvoice(InvoiceValidationRequestDTO invoiceValidationReq);

	public InvoiceDTO findInvoiceByInvoiceNumber(String invoiceNo);

}

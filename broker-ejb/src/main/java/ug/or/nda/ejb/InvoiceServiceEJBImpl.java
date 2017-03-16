package ug.or.nda.ejb;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import ug.or.nda.dto.InvoiceDTO;
import ug.or.nda.dto.InvoiceValidationRequestDTO;
import ug.or.nda.dto.InvoiceValidationResponseDTO;

@Stateless
public class InvoiceServiceEJBImpl implements InvoiceServiceEJBI {
	
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public InvoiceValidationResponseDTO  validateInvoice(InvoiceValidationRequestDTO request){
		logger.info(request);
		return null;
	}
	
	@Override
	public InvoiceDTO findInvoiceByInvoiceNumber(String invoiceNo){
		InvoiceDTO invoiceDTO = new InvoiceDTO();
		return invoiceDTO;
	}

}

package ug.or.nda.ejb;

import javax.ejb.Stateless;

import ug.or.nda.dto.InvoiceDTO;
import ug.or.nda.dto.InvoiceValidationRequestDTO;
import ug.or.nda.dto.InvoiceValidationResponseDTO;
import ug.or.nda.exceptions.BrokerException;
import ug.or.nda.exceptions.RequestHeaderMissingException;
import ug.or.nda.ws.Invoice;
import ug.or.nda.ws.InvoiceValidationRequest;
import ug.or.nda.ws.InvoiceValidationResponse;
import ug.or.nda.ws.RequestHeader;

@Stateless
public class InvoiceValidationRequestConverterImpl implements InvoiceValidationRequestConverterI {
	
	@Override
	public InvoiceValidationRequest convert(InvoiceValidationRequestDTO validationDTO) throws BrokerException{
		
		InvoiceValidationRequest req = new InvoiceValidationRequest();
		
		req.setInvoiceNo( validationDTO.getInvoiceNo() );
		
		RequestHeader header = new RequestHeader();
		
		if(validationDTO.getRequestHeader()==null)
			throw new RequestHeaderMissingException("Request header not provided!");
		
		header.setPassword(validationDTO.getRequestHeader().getPassword());
		header.setSystemToken(validationDTO.getRequestHeader().getSystemToken());
		header.setUsername(  validationDTO.getRequestHeader().getUsername() );
		
		req.setRequestHeader(header);
		
		return req;
	}

	@Override
	public InvoiceValidationResponseDTO convert(InvoiceValidationResponse resp, boolean paymentExists, String systemMessage) throws BrokerException {
		InvoiceValidationResponseDTO response = new InvoiceValidationResponseDTO();
		
		response.setStatusCode(resp.getStatusCode());
		response.setStatusMessage(resp.getStatusMessage());
		
		if(resp.getStatusCode()==0){
			InvoiceDTO invoiceDTO = new InvoiceDTO();
			Invoice invoice = resp.getInvoice();
			invoiceDTO.setAmount( invoice.getAmount() );
			invoiceDTO.setCurrencyCode( invoice.getCurrencyCode() );
			invoiceDTO.setCustomerName( invoice.getCustomerName() );
			invoiceDTO.setDescription( invoice.getDescription());
			invoiceDTO.setDueDate( invoice.getDueDate().toGregorianCalendar().getTime()  );
			invoiceDTO.setInvoiceNo( invoice.getInvoiceNo() );
			invoiceDTO.setReference1( invoice.getReference1() );
			invoiceDTO.setStatus( invoice.getStatus() );
			invoiceDTO.setPaymentExists(paymentExists);
			invoiceDTO.setSystemMessage(systemMessage);
			response.setInvoice(invoiceDTO);
			
		}
		
		return response;
	}


}

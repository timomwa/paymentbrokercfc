package ug.or.nda.service;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;

import ug.or.nda.dto.InvoiceValidationResponseDTO;
import ug.or.nda.ejb.InvoiceServiceEJBI;
import ug.or.nda.ejb.PaymentPushEJBI;
import ug.or.nda.dto.InvoiceValidationRequestDTO;

import org.jboss.wsf.spi.annotation.WebContext;


@WebService( name="validationService" , targetNamespace="http://services.nda.or.ug")
@WebContext(contextRoot = "/broker", urlPattern="/invoice/validation/v1.0")
@Stateless
public class InvoiceService  {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Resource
	private WebServiceContext wsContext; 
	
	
	@EJB
	private InvoiceServiceEJBI invoiceService;
	
	
	@WebMethod
	public InvoiceValidationResponseDTO  validateInvoice(InvoiceValidationRequestDTO invoiceValidationReq){
		
		logger.info(invoiceValidationReq);
		
		InvoiceValidationResponseDTO response = null;
		
		try{
		
			response = invoiceService.validateInvoice(invoiceValidationReq, getIPAddress());
	
		}catch(Exception e){
		
			logger.error(e.getMessage(), e);
		
		}
		
		return response;
	}
	
	
	private String getIPAddress() {
		try{
			MessageContext mc = wsContext.getMessageContext();
		    HttpServletRequest req = (HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST); 
		    return req.getRemoteAddr();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return "1.1.1.1";//Fake IP
	}
	
}

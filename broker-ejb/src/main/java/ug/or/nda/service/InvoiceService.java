package ug.or.nda.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import ug.or.nda.dto.InvoiceValidationResponseDTO;
import ug.or.nda.ejb.InvoiceServiceEJBI;
import ug.or.nda.dto.InvoiceValidationRequestDTO;

import org.jboss.wsf.spi.annotation.WebContext;


@WebService( name="invoiceservice" , targetNamespace="http://services.nda.or.ug")
@WebContext(contextRoot = "/broker", urlPattern="/")
@Stateless
public class InvoiceService  {
	
	private Logger logger = Logger.getLogger(getClass());
	
	//@EJB(mappedName = "java:global/Xi/BeneficiaryBean")
	
	//jndi:paymentbroker-ear-1.0/InvoiceServiceEJBImpl/remote
	//jndi:paymentbroker-ear-1.0/InvoiceServiceEJBImpl/local-ug.or.nda.ejb.InvoiceServiceEJBI
	//jndi:paymentbroker-ear-1.0/InvoiceServiceEJBImpl/local
	//@EJB
	//private InvoiceServiceEJBI invoiceService0;
	
	
	//@EJB(name="InvoiceServiceEJBImpl", beanName="InvoiceServiceEJBImpl", beanInterface=InvoiceServiceEJBI.class, lookup="paymentbroker-ear-1.0/InvoiceServiceEJBImpl/local")
	//private InvoiceServiceEJBI invoiceService;
	
	//@EJB(lookup="paymentbroker-ear-1.0/InvoiceServiceEJBImpl/local-ug.or.nda.ejb.InvoiceServiceEJBI")
	//private InvoiceServiceEJBI invoiceService1;
	
	//@EJB(lookup="paymentbroker-ear-1.0/InvoiceServiceEJBImpl/local")
	//private InvoiceServiceEJBI invoiceService2;
	
	
	//@EJB(lookup="paymentbroker-ear-1.0/InvoiceServiceEJBImpl/local")
	//private InvoiceServiceEJBI invoiceService3;
	
	//@EJB(lookup="paymentbroker-ear-1.0/InvoiceServiceEJBImpl/local-ug.or.nda.ejb.InvoiceServiceEJBI")
	//private InvoiceServiceEJBI invoiceService4;
	
	@EJB
	private InvoiceServiceEJBI invoiceService5;
	
	//@EJB(lookup="paymentbroker-ear-1.0/InvoiceServiceEJBImpl/local-ug.or.nda.ejb.InvoiceServiceEJBI")
	//private InvoiceServiceEJBI invoiceService6;
	
	//@EJB(lookup="paymentbroker-ear-1.0/InvoiceServiceEJBImpl/local")
	//private InvoiceServiceEJBI invoiceService7;
	
	@WebMethod
	public InvoiceValidationResponseDTO  validateInvoice(InvoiceValidationRequestDTO invoiceValidationReq){
		
		logger.info(invoiceValidationReq);
		
		//logger.info(" invoiceService0 --> "+invoiceService0);
		
		//logger.info(" invoiceService --> "+invoiceService);
		
		//logger.info(" invoiceService1 --> "+invoiceService1);
		
		//logger.info(" invoiceService2 --> "+invoiceService2);
		
		//logger.info(" invoiceService3 --> "+invoiceService3);
		
		//logger.info(" invoiceService4 --> "+invoiceService4);
		
		logger.info(" invoiceService5 --> "+invoiceService5);
		
		//logger.info(" invoiceService6 --> "+invoiceService6);
		
		//logger.info(" invoiceService7 --> "+invoiceService7);
		
		InvoiceValidationResponseDTO response = null;
		
		try{
		
			response = invoiceService5.validateInvoice(invoiceValidationReq);
	
		}catch(Exception e){
		
			logger.error(e.getMessage(), e);
		
		}
		
		return response;
	}
	
}

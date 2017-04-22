package ug.or.nda.ejb;

import java.net.MalformedURLException;
import java.net.URL;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

import ug.or.nda.dto.InvoiceDTO;
import ug.or.nda.dto.InvoiceValidationRequestDTO;
import ug.or.nda.dto.InvoiceValidationResponseDTO;
import ug.or.nda.entities.PaymentNotificationRawLog;
import ug.or.nda.exceptions.BrokerException;
import ug.or.nda.services.InvoiceServiceService;
import ug.or.nda.services.ValidationService;
import ug.or.nda.ws.InvoiceValidationRequest;
import ug.or.nda.ws.InvoiceValidationResponse;
import ug.or.nda.ws.RequestHeader;
import ug.or.nda.wsi.InvoiceWebService;
import ug.or.nda.wsi.InvoiceWebService_Service;
import ug.or.nda.wsi.ValidationWebService;

@Stateless
public class InvoiceServiceEJBImpl implements InvoiceServiceEJBI {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@EJB
	private InvoiceValidationRequestConverterI invoiceValidatorReqConverter;
	
	@EJB
	private PaymentPushEJBI paymentPushEJB;
	
	
	@EJB
	private PaymentNotificationEJBI paymentNotificationEJB;
	

	@Override
	public InvoiceValidationResponseDTO  validateInvoice(InvoiceValidationRequestDTO request) throws BrokerException{
		
		InvoiceValidationResponseDTO validationReq = new  InvoiceValidationResponseDTO();
		
		logger.info(request);
		
		URL wsdlLocation = null;
		String endpoint = "http://ndaendopoint/ndamisws/invoice/validation/v1.0";
		try {
			wsdlLocation = new URL(endpoint+"?wsdl");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
	
		QName serviceName = new QName("http://wsi.nda.or.ug","invoiceWebService");
		
		InvoiceWebService service1 = new InvoiceWebService(wsdlLocation,serviceName);
		ValidationWebService port1 = service1.getValidationWebServicePort();
        InvoiceValidationRequest req = invoiceValidatorReqConverter.convert(request);
        
        
        InvoiceValidationResponse resp  = port1.validateInvoice(req);
        logger.info("<<<<<< [Server Response] " + resp);
        
        boolean paymentExists = paymentPushEJB.isInQueue(request.getInvoiceNo());
        
        PaymentNotificationRawLog rawlog =  paymentNotificationEJB.findLastRawLogByInvoiceNo(request.getInvoiceNo());
        
        String systemMsg = (rawlog!=null) ? rawlog.getSystemMsg() : "";
        
        validationReq =  invoiceValidatorReqConverter.convert(resp,paymentExists, systemMsg);
        
        return validationReq;
	}
	
	@Override
	public InvoiceDTO findInvoiceByInvoiceNumber(String invoiceNo)  throws BrokerException{
		
		InvoiceDTO invoiceDTO = null;
		
		URL wsdlLocation = null;
		String endpoint = "http://ndaendopoint/ndamisws/invoice/validation/v1.0";
		try {
			wsdlLocation = new URL(endpoint+"?wsdl");
		} catch (MalformedURLException e1) {
			logger.error(e1.getMessage(), e1);
		}
	
		QName serviceName = new QName("http://wsi.nda.or.ug","invoiceWebService");
		
		InvoiceWebService service1 = new InvoiceWebService(wsdlLocation,serviceName);
		ValidationWebService port1 = service1.getValidationWebServicePort();
        
        
        InvoiceValidationRequest req = new InvoiceValidationRequest();
        req.setInvoiceNo(invoiceNo);
        
        RequestHeader header = new RequestHeader();
        header.setPassword("");
        req.setRequestHeader(header);
        
        InvoiceValidationResponse resp  = port1.validateInvoice(req);
        logger.info("<<<<<< [Server Response] " + resp);
        
        if(resp.getStatusCode()==0){
        	
        	boolean paymentExists = paymentPushEJB.isInQueue(invoiceNo);
        	
        	PaymentNotificationRawLog rawlog =  paymentNotificationEJB.findLastRawLogByInvoiceNo(invoiceNo);
            String systemMsg = (rawlog!=null) ? rawlog.getSystemMsg() : "";
        	InvoiceValidationResponseDTO  validationReq =  invoiceValidatorReqConverter.convert(resp,paymentExists,systemMsg);
        	
        	invoiceDTO = validationReq.getInvoice();
        }
        
		return invoiceDTO;
	}

}

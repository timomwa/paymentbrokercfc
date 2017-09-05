package ug.or.nda.ejb;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

import ug.or.nda.constant.AppPropertyHolder;
import ug.or.nda.constant.ResponseCode;
import ug.or.nda.constant.ServiceMessageCodes;
import ug.or.nda.constant.Status;
import ug.or.nda.constant.TerminalColorCodes;
import ug.or.nda.dto.InvoiceDTO;
import ug.or.nda.dto.InvoiceValidationRequestDTO;
import ug.or.nda.dto.InvoiceValidationResponseDTO;
import ug.or.nda.dto.QueryDTO;
import ug.or.nda.entities.Invoice;
import ug.or.nda.entities.InvoiceValidationRawLog;
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
	
	@PersistenceContext(unitName=AppPropertyHolder.PRIMARY_PERSISTENT_UNIT)
	protected EntityManager em;
	
	private Logger logger = Logger.getLogger(getClass());
	
	@EJB
	private InvoiceValidationRequestConverterI invoiceValidatorReqConverter;
	
	@EJB
	private PaymentPushEJBI paymentPushEJB;
	
	@EJB
	private IPWhitelistEJBI ipWhitelistEJB;
	
	@EJB
	private PaymentNotificationRawLogEJBI paymentNotificationRawLogEJB;
	
	
	

	@Override
	public InvoiceValidationResponseDTO  validateInvoice(InvoiceValidationRequestDTO request, String ipAddress) throws BrokerException{
		
		
		InvoiceValidationRawLog invoiceValidationLog= new InvoiceValidationRawLog();
		InvoiceValidationResponseDTO validationResp = new  InvoiceValidationResponseDTO();
		Status status = Status.JUST_IN;
		String systemMsg = "";
		
		try{
			
			invoiceValidationLog.setSourcehost(ipAddress);
			invoiceValidationLog.setPayload( request.toString()  );
			invoiceValidationLog.setStatus( status );
			invoiceValidationLog.setSystemID( request.getRequestHeader().getSystemID() );
			invoiceValidationLog.setInvoiceNo( request.getInvoiceNo() );
			logger.info(TerminalColorCodes.ANSI_BLUE + " BROKER INCOMING from ["+ipAddress+"] >>>>>>>>> "+request+TerminalColorCodes.ANSI_RESET);
			
			boolean hostAllowed = ipWhitelistEJB.isWhitelisted(ipAddress);

			logger.info(request);
			
			if(!hostAllowed)
				throw new BrokerException("Error: Forbidden (Caller not allowed. Kindly ask admin to whitelist you!)- "+ServiceMessageCodes.CALLER_NOT_ALLOWED);
			
			URL wsdlLocation = null;
			String endpoint = AppPropertyHolder.BASE_WS_URL.concat("/ndamisws/invoice/validation/v1.0");
			
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
	        
	        //InvoiceValidationRawLog rawlog =  paymentNotificationRawLogEJB.findLastRawLogByInvoiceNo(request.getInvoiceNo());
	        
	        systemMsg = Status.SUCCESS.name();//  (rawlog!=null) ? rawlog.getSystemMsg() : "";
	        status = Status.SUCCESS;
	        
	        validationResp =  invoiceValidatorReqConverter.convert(resp,paymentExists, systemMsg);
	        
		}catch(BrokerException be){
			
			systemMsg = be.getMessage();
			status = Status.FAILED_TEMPORARILY;
			
			logger.error(be.getMessage(), be);
			
			validationResp.setStatusCode( ResponseCode.ERROR.getCode() );
			validationResp.setStatusMessage( systemMsg );
			
	        
		}catch(Exception e){
			
			systemMsg = "Problem occurred. Try again later.";
			status = Status.FAILED_PERMANENTLY;
			
			logger.error(e.getMessage(), e);
			
			validationResp.setStatusCode( ResponseCode.ERROR.getCode() );
			validationResp.setStatusMessage( ResponseCode.ERROR.name() );
			
			invoiceValidationLog.setSystemMsg( "A problem occurred: "+e.getMessage() );
	        invoiceValidationLog.setStatus( Status.FAILED_PERMANENTLY );
		
		}finally{
			
			try{
				
				if(invoiceValidationLog!=null){
					invoiceValidationLog.setSystemMsg( systemMsg );
			        invoiceValidationLog.setStatus( status );
			        invoiceValidationLog = save( invoiceValidationLog);
				}
				
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}
			
		}
        
        return validationResp;
	}
	
	@Override
	public InvoiceValidationRawLog save(InvoiceValidationRawLog notificationRawLog) throws Exception{
		return em.merge(notificationRawLog);
	}
	
	@Override
	public InvoiceDTO findInvoiceByInvoiceNumber(String invoiceNo)  throws BrokerException{
		
		InvoiceDTO invoiceDTO = null;
		
		URL wsdlLocation = null;
		String endpoint = AppPropertyHolder.BASE_WS_URL.concat("/ndamisws/invoice/validation/v1.0");
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
        	
        	InvoiceValidationRawLog rawlog =  paymentNotificationRawLogEJB.findLastRawLogByInvoiceNo(invoiceNo);
            String systemMsg = (rawlog!=null) ? rawlog.getSystemMsg() : "";
        	InvoiceValidationResponseDTO  validationReq =  invoiceValidatorReqConverter.convert(resp,paymentExists,systemMsg);
        	
        	invoiceDTO = validationReq.getInvoice();
        }
        
		return invoiceDTO;
	}

	

	
}

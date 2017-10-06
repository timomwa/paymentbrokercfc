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
import org.jboss.wsf.spi.annotation.WebContext;

import ug.or.nda.dto.PaymentNotificationRequestDTO;
import ug.or.nda.dto.PaymentNotificationResponseDTO;
import ug.or.nda.ejb.PaymentNotificationEJBI;

@WebService(name="paymentService" , targetNamespace="http://services.nda.or.ug")
@WebContext(contextRoot = "/broker", urlPattern="/invoice/payment/v1.0")
@Stateless
public class PaymentService {
	
	@Resource
	private WebServiceContext wsContext; 
	
	@EJB
	private PaymentNotificationEJBI paymentNotification;
	
	private Logger logger = Logger.getLogger(getClass());
	
	@WebMethod
	public PaymentNotificationResponseDTO paymentNotification(PaymentNotificationRequestDTO paymentNotificationReq){
		
		logger.info(paymentNotificationReq);
		
		PaymentNotificationResponseDTO resp = null;
		
		try{
		
			resp =  paymentNotification.paymentNotification(paymentNotificationReq, getIPAddress());
		
		}catch(Exception e){
		
			logger.error(e.getMessage(), e);
		
		}
		
		return resp;
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

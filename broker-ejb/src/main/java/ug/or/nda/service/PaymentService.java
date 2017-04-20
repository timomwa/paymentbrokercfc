package ug.or.nda.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.jboss.wsf.spi.annotation.WebContext;

import ug.or.nda.dto.PaymentNotificationResponseDTO;
import ug.or.nda.ejb.PaymentNotificationEJBI;
import ug.or.nda.dto.PaymentNotificationRequestDTO;

@WebService(name="paymentService" , targetNamespace="http://services.nda.or.ug")
@WebContext(contextRoot = "/broker", urlPattern="/invoice/payment/v1.0")
@Stateless
public class PaymentService {
	
	@EJB
	private PaymentNotificationEJBI paymentNotification;
	
	private Logger logger = Logger.getLogger(getClass());
	
	@WebMethod
	public PaymentNotificationResponseDTO paymentNotification(PaymentNotificationRequestDTO paymentNotificationReq){
		
		logger.info(paymentNotificationReq);
		
		PaymentNotificationResponseDTO resp = null;
		
		try{
		
			resp =  paymentNotification.paymentNotification(paymentNotificationReq);
		
		}catch(Exception e){
		
			logger.error(e.getMessage(), e);
		
		}
		
		return resp;
	}

}

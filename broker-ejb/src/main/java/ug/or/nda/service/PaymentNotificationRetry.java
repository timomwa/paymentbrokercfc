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

import ug.or.nda.dto.PaymentNotificationResponseDTO;
import ug.or.nda.dto.QueryDTO;
import ug.or.nda.ejb.PaymentRetryEJBI;

@WebService(name="paymentRetryService" , targetNamespace="http://services.nda.or.ug")
@WebContext(contextRoot = "/broker", urlPattern="/payment/retry/v1.0")
@Stateless
public class PaymentNotificationRetry {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@EJB
	private PaymentRetryEJBI paymentRetryEJB;

	@Resource
	private WebServiceContext wsContext; 
	
	@WebMethod
	public PaymentNotificationResponseDTO retry(QueryDTO paymentRetryDTO){
		return paymentRetryEJB.retry(paymentRetryDTO, getIPAddress());
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

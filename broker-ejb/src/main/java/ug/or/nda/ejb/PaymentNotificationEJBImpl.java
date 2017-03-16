package ug.or.nda.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import ug.or.nda.constant.ResponseCode;
import ug.or.nda.dao.PaymentNotificationDAOI;
import ug.or.nda.entities.PaymentNotification;
import ug.or.nda.exceptions.BrokerException;
import ug.or.nda.exceptions.InvalidInvoiceException;
import ug.or.nda.dto.InvoiceDTO;
import ug.or.nda.dto.PaymentNotificationRequestDTO;
import ug.or.nda.dto.PaymentNotificationResponseDTO;

@Stateless
public class PaymentNotificationEJBImpl implements PaymentNotificationEJBI {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private PaymentNotificationDAOI paymentNotificationDAO;
	
	@EJB
	private PaymentNotificatinConverterI paymentNotificationConverter;
	
	@EJB
	private InvoiceServiceEJBI invoiceService;
	
	
	public PaymentNotification save(PaymentNotification paymentNotification) throws Exception{
		return paymentNotificationDAO.save(paymentNotification);
	}

	@Override
	public PaymentNotificationResponseDTO paymentNotification(PaymentNotificationRequestDTO request) {
		
		PaymentNotificationResponseDTO response = new PaymentNotificationResponseDTO();
		
		logger.info(request);
		
		PaymentNotification notification = paymentNotificationConverter.convert(request.getPaymentNotification());
		
		try {
			
			InvoiceDTO invoice = invoiceService.findInvoiceByInvoiceNumber(notification.getInvoiceNo());
			
			if(invoice==null)
				throw new InvalidInvoiceException("Invoice with the number \""+notification.getInvoiceNo()+"\"");
			
			notification = save(notification);
			
			response.setStatusCode(ResponseCode.SUCCESS.getCode());
			response.setStatusMessage(ResponseCode.SUCCESS.name());
			
		}catch(BrokerException be){
			
			logger.error(be.getMessage(), be);
			
			response.setStatusCode(ResponseCode.ERROR.getCode());
			response.setStatusMessage(be.getMessage());
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			response.setStatusCode(ResponseCode.ERROR.getCode());
			response.setStatusMessage(ResponseCode.ERROR.name());
			
		}
		
		return response;
	}

}

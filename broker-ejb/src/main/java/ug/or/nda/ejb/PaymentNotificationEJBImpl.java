package ug.or.nda.ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import ug.or.nda.constant.AppPropertyHolder;
import ug.or.nda.constant.ResponseCode;
import ug.or.nda.constant.Status;
import ug.or.nda.dto.InvoiceDTO;
import ug.or.nda.dto.PaymentNotificationRequestDTO;
import ug.or.nda.dto.PaymentNotificationResponseDTO;
import ug.or.nda.entities.PaymentNotification;
import ug.or.nda.exceptions.BrokerException;
import ug.or.nda.exceptions.InvalidInvoiceException;

@Stateless
public class PaymentNotificationEJBImpl implements PaymentNotificationEJBI {
	
	private Logger logger = Logger.getLogger(getClass());
	
	
	@EJB
	private PaymentNotificatinConverterI paymentNotificationConverter;
	
	@EJB
	private InvoiceServiceEJBI invoiceService;
	
	@EJB
	private PaymentPushEJBI paymentPushEJB;
	
	@PersistenceContext(unitName=AppPropertyHolder.PRIMARY_PERSISTENT_UNIT)
	protected EntityManager em;
	
	
	
	public PaymentNotification save(PaymentNotification paymentNotification) throws Exception{
		return em.merge(paymentNotification);
	}

	@Override
	public PaymentNotificationResponseDTO paymentNotification(PaymentNotificationRequestDTO request) {
		
		PaymentNotificationResponseDTO response = new PaymentNotificationResponseDTO();
		
		logger.info(request);
		
		
		try {
			PaymentNotification notification = paymentNotificationConverter.convert(request.getPaymentNotification());
			
			InvoiceDTO invoice = invoiceService.findInvoiceByInvoiceNumber(notification.getInvoiceNo());
			
			if(invoice==null)
				throw new InvalidInvoiceException("Invoice with the number \""+notification.getInvoiceNo()+"\" Not found!");
			
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

	@Override
	public void pushPayments() {
		try{
			List<PaymentNotification> notifications = fetchUnprocessedNotifications(5);
			for( PaymentNotification payment : notifications){
				
				try{
					
					paymentPushEJB.pushPayment(payment);
					
				}catch(Exception e){
					logger.error(e.getMessage(), e);
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentNotification> fetchUnprocessedNotifications(int limit) {
		
		List<PaymentNotification> payments = new ArrayList<PaymentNotification>();
		
		try{
			
			List<Status> statuses = Arrays.asList( Status.FAILED_TEMPORARILY, Status.JUST_IN );
			Query qry = em.createQuery("from PaymentNotification WHERE status in (:statuses)  ORDER BY earliestRetryTime desc");
			qry.setParameter("statuses", statuses);
			qry.setParameter("earliestRetryTime", new Date());
			qry.setMaxResults(limit);
			payments = qry.getResultList();
			
		}catch(NoResultException re){
		
			logger.warn("no pamyments");
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
		
		}
		
		return payments;
	}

}

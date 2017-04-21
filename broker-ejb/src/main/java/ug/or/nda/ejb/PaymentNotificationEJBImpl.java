package ug.or.nda.ejb;

import java.math.BigDecimal;
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
import ug.or.nda.constant.TerminalColorCodes;
import ug.or.nda.dto.InvoiceDTO;
import ug.or.nda.dto.PaymentNotificationDTO;
import ug.or.nda.dto.PaymentNotificationRequestDTO;
import ug.or.nda.dto.PaymentNotificationResponseDTO;
import ug.or.nda.entities.PaymentNotification;
import ug.or.nda.entities.PaymentNotificationRawLog;
import ug.or.nda.exceptions.BrokerException;
import ug.or.nda.exceptions.InvalidInvoiceException;
import ug.or.nda.wsi.InvoiceStatus;

@Stateless
public class PaymentNotificationEJBImpl implements PaymentNotificationEJBI {
	
	public static final String CALLER_NOT_ALLOWED = "BE401";//Broker exception Error 401 - Unauthorized


	private Logger logger = Logger.getLogger(getClass());
	
	
	@EJB
	private PaymentNotificatinConverterI paymentNotificationConverter;
	
	@EJB
	private InvoiceServiceEJBI invoiceService;
	
	@EJB
	private PaymentPushEJBI paymentPushEJB;
	
	@EJB
	private IPWhitelistEJBI ipWhitelistEJB;
	
	@PersistenceContext(unitName=AppPropertyHolder.PRIMARY_PERSISTENT_UNIT)
	protected EntityManager em;
	
	
	
	public PaymentNotification save(PaymentNotification paymentNotification) throws Exception{
		return em.merge(paymentNotification);
	}

	@Override
	public PaymentNotificationResponseDTO paymentNotification(PaymentNotificationRequestDTO request, String ipAddress) {
		
		PaymentNotificationResponseDTO response = new PaymentNotificationResponseDTO();
		
		logger.info(TerminalColorCodes.ANSI_BLUE + " BROKER INCOMING from ["+ipAddress+"] >>>>>>>>> "+request+TerminalColorCodes.ANSI_RESET);
		
		PaymentNotificationRawLog notificationRawLog = null;
		
		String systemMsg = "";
		
		try {
			
			boolean hostAllowed = ipWhitelistEJB.isWhitelisted(ipAddress);
			
			if(!hostAllowed)
				throw new BrokerException("Error: Forbidden - "+CALLER_NOT_ALLOWED);
			
			PaymentNotification notification = paymentNotificationConverter.convert(request.getPaymentNotification());
			
			notificationRawLog = paymentNotificationConverter.convertToRawLog(notification);
			
			try{
				notificationRawLog.setSourcehost(ipAddress);
				notificationRawLog.setSystemMsg(systemMsg);
				notificationRawLog = save(notificationRawLog);
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}
			
			validate(request);
			
			InvoiceDTO invoice = invoiceService.findInvoiceByInvoiceNumber(notification.getInvoiceNo());
			
			if(invoice==null)
				throw new InvalidInvoiceException("Invoice with the number \""+notification.getInvoiceNo()+"\" Not found!");
			
			if(invoice.getStatus()==InvoiceStatus.PAID || invoice.getStatus()==InvoiceStatus.EXPIRED)
				throw new InvalidInvoiceException("Invoice Rejected because this invoice has the status \""+invoice.getStatus()+"\".");
			
			logger.info(" Invoice found---> "+TerminalColorCodes.ANSI_CYAN +invoice.toString()+TerminalColorCodes.ANSI_RESET);
			
			boolean paymentAlreadyInQueue = paymentPushEJB.isInQueue(notification);
			
			if(paymentAlreadyInQueue)
				throw new BrokerException("Rejected! A similar payment is in the queue.");
			
			int comparison = invoice.getAmount().compareTo( BigDecimal.valueOf( notification.getAmount()) );
			
			if(comparison>0)
				throw new BrokerException("The amount paid is less than the invoice amount! The amount required is "+invoice.getCurrencyCode()+". "+invoice.getAmount().doubleValue());
			if(comparison<0)
				throw new BrokerException("The amount paid is more than the invoice amount! The amount required is only "+invoice.getCurrencyCode()+". "+invoice.getAmount().doubleValue());
			
			notification = save(notification);
			
			response.setStatusCode(ResponseCode.SUCCESS.getCode());
			response.setStatusMessage(ResponseCode.SUCCESS.name());
			
			systemMsg = ResponseCode.SUCCESS.name();
			
		}catch(BrokerException be){
			
			systemMsg = be.getMessage();
			
			logger.error(TerminalColorCodes.ANSI_RED + be.getMessage() +TerminalColorCodes.ANSI_YELLOW+" caller being ["+ipAddress+"]"+TerminalColorCodes.ANSI_RESET);
			
			response.setStatusCode(ResponseCode.ERROR.getCode());
			response.setStatusMessage(be.getMessage());
			
		} catch (Exception e) {
			systemMsg = e.getMessage();
			logger.error(e.getMessage(), e);
			
			response.setStatusCode(ResponseCode.ERROR.getCode());
			response.setStatusMessage(ResponseCode.ERROR.name());
			
		}finally{
			try{
				if(notificationRawLog!=null){
					notificationRawLog.setSystemMsg(systemMsg);
					notificationRawLog = save(notificationRawLog);
				}
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}
		}
		
		return response;
	}

	@Override
	public PaymentNotificationRawLog save(PaymentNotificationRawLog notificationRawLog) throws Exception{
		return em.merge(notificationRawLog);
	}

	private void validate(PaymentNotificationRequestDTO request) throws BrokerException{
		if(request==null)
			throw new BrokerException("Null request received!");
		PaymentNotificationDTO notification = request.getPaymentNotification();
		if(notification==null)
			throw new BrokerException("Payment notification object not found!");
		if(notification.getAmount()==null)
			throw new BrokerException("Payment amount not supplied! Kindly specify this.");
		if(notification.getCurrencyCode()==null || notification.getCurrencyCode().isEmpty())
			throw new BrokerException("Currency code not supplied! Kindly specify this.");
		if(notification.getInvoiceNo()==null || notification.getInvoiceNo().isEmpty())
			throw new BrokerException("Invoice Number not supplied! Kindly specify this");
		if(notification.getPaymentMode()==null || notification.getPaymentMode().isEmpty())
			throw new BrokerException("Payment mode not supplied! Kindly specify this");
		if(notification.getTransactionDate()==null )
			throw new BrokerException("Transaction date not supplied! Kindly specify this");
		if(notification.getTransactionRef()==null ||  notification.getTransactionRef().isEmpty())
			throw new BrokerException("Transaction reference not supplied! Kindly specify this");
		
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
			Query qry = em.createQuery("from PaymentNotification WHERE status in (:statuses) AND earliestRetryTime <= :earliestRetryTime ORDER BY earliestRetryTime desc");
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

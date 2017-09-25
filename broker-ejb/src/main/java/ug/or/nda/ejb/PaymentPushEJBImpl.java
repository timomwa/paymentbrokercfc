package ug.or.nda.ejb;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

import ug.or.nda.constant.AppPropertyHolder;
import ug.or.nda.constant.Status;
import ug.or.nda.entities.PaymentNotification;
import ug.or.nda.entities.PaymentNotificationRawLog;
import ug.or.nda.exceptions.BrokerException;
import ug.or.nda.ws.PaymentNotificationRequest;
import ug.or.nda.ws.PaymentNotificationResponse;
import ug.or.nda.wsp.PaymentWebService;
import ug.or.nda.wsp.PaymentWebService_Service;


@Stateless
public class PaymentPushEJBImpl implements PaymentPushEJBI {
	
	public static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
	public static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private Logger logger = Logger.getLogger(getClass());
	
	@PersistenceContext(unitName=AppPropertyHolder.PRIMARY_PERSISTENT_UNIT)
	protected EntityManager em;
	
	@EJB
	private PaymentNotificatinConverterI paymentNotifConverterEJBI;
	
	private PaymentWebService paymentport;
	
	
	@PostConstruct
	public void init(){
		try{
			URL wsdlLocation = null;
			String endpoint = AppPropertyHolder.BASE_WS_URL.concat("/ndamisws/invoice/payment/v1.0");
			try {
				wsdlLocation = new URL(endpoint+"?wsdl");
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		logger.info( " --> wsdlLocation :: "+wsdlLocation  );
			QName serviceName = new QName("http://wsp.nda.or.ug","paymentWebService");
			
			PaymentWebService_Service service1 = new PaymentWebService_Service(wsdlLocation,serviceName);
			paymentport = service1.getPaymentWebServicePort();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
	}
	
	public void pushPayment(PaymentNotification payment) throws BrokerException{
		
		PaymentNotificationRawLog paymentNotificationRawLog = new PaymentNotificationRawLog();
		paymentNotificationRawLog.setInvoiceNo( payment.getInvoiceNo() );
		paymentNotificationRawLog.setSourcehost( "localhost" );
		paymentNotificationRawLog.setSystemID("AUTO-RETRY-BOT");
		
		try{
			
			PaymentNotificationRequest paymentNotif = paymentNotifConverterEJBI.convert(payment);
			
			PaymentNotificationResponse resp = paymentport.paymentNotification(paymentNotif);
			
			
			String msg = resp.getStatusMessage();
			Status status = Status.SUCCESS;
			
			if(resp.getStatusCode()==0){
				
				payment.setStatus(Status.SUCCESS);
				
			}else{
				
				
				if(msg.contains("A payment has already been made against this invoice")){
					
					msg = msg+ ", So we denote this as a success!";
					status = Status.SUCCESS;
					payment.setStatus(status);
				
				}else{
					
					status = Status.FAILED_TEMPORARILY;
				
				}
				
				if(payment.getRetrycount().compareTo( payment.getMaxretries()-1  )>=0 && status != Status.SUCCESS){
					
					status = Status.FAILED_PERMANENTLY;
					payment.setStatus(status);
					
				}else{
					
					Calendar date = Calendar.getInstance();
					long t= date.getTimeInMillis();
					Date afterAddingTenMins=new Date(t + (10 *  ONE_MINUTE_IN_MILLIS));
					payment.setEarliestRetryTime(afterAddingTenMins);
					payment.setStatus(status);
					
				}
				
				
				
				
			}
			
			payment.setRetrycount( payment.getRetrycount() + 1 );
			payment = em.merge(payment);
			
			logger.info(resp);
			
			paymentNotificationRawLog.setSystemMsg( msg );
			paymentNotificationRawLog.setStatus( status );
			paymentNotificationRawLog.setPayload( payment.toString() );
			paymentNotificationRawLog = em.merge(paymentNotificationRawLog);
			
		}catch(BrokerException be){
			logger.error(be.getMessage(), be);
			throw be;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isInQueue(PaymentNotification notification) {
		return isInQueue(notification.getInvoiceNo());
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean isInQueue(String  invoiceNo) {
		try{
			Query qry = em.createQuery("from PaymentNotification WHERE  invoiceNo = :invoiceNo ");
			qry.setParameter("invoiceNo", invoiceNo);
			List<PaymentNotification> notifications = qry.getResultList();
			return notifications.size()>0;
		}catch(NoResultException e){
			logger.warn("No payment notif in queue found");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return false;
	}
	
	
	
	

}

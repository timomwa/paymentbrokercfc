package ug.or.nda.ejb;

import java.net.MalformedURLException;
import java.net.URL;
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
import ug.or.nda.exceptions.BrokerException;
import ug.or.nda.ws.PaymentNotificationRequest;
import ug.or.nda.ws.PaymentNotificationResponse;
import ug.or.nda.wsp.PaymentWebService;
import ug.or.nda.wsp.PaymentWebService_Service;


@Stateless
public class PaymentPushEJBImpl implements PaymentPushEJBI {
	
	static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
	
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
			String endpoint = "http://ndaendopoint/ndamisws/invoice/payment/v1.0";
			try {
				wsdlLocation = new URL(endpoint+"?wsdl");
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		
			QName serviceName = new QName("http://wsp.nda.or.ug","paymentWebService");
			
			PaymentWebService_Service service1 = new PaymentWebService_Service(wsdlLocation,serviceName);
			paymentport = service1.getPaymentWebServicePort();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
	}
	
	public void pushPayment(PaymentNotification payment) throws BrokerException{
		try{
			
			PaymentNotificationRequest paymentNotif = paymentNotifConverterEJBI.convert(payment);
			PaymentNotificationResponse resp = paymentport.paymentNotification(paymentNotif);
			
			if(resp.getStatusCode()==0){
				
				payment.setStatus(Status.SUCCESS);
				
			}else{
				
				Status status = Status.FAILED_TEMPORARILY;
				
				if(payment.getRetrycount().compareTo( payment.getMaxretries()-1  )>=0){
					status = Status.FAILED_PERMANENTLY;
				}else{
					Calendar date = Calendar.getInstance();
					long t= date.getTimeInMillis();
					Date afterAddingTenMins=new Date(t + (10 * ONE_MINUTE_IN_MILLIS));
					payment.setEarliestRetryTime(afterAddingTenMins);
				}
				
				payment.setStatus(status);
				
			}
			
			payment.setRetrycount( payment.getRetrycount() + 1 );
			payment = em.merge(payment);
			
			logger.info(resp);
			
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
		try{
			List<Status> statuses = Arrays.asList( Status.JUST_IN, Status.IN_QUEUE,  Status.PROCESSING, Status.FAILED_TEMPORARILY);
			Query qry = em.createQuery("from PaymentNotification WHERE retrycount<maxretries AND status IN (:statuses) AND invoiceNo = :invoiceNo ");
			qry.setParameter("statuses", statuses);
			qry.setParameter("invoiceNo", notification.getInvoiceNo());
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

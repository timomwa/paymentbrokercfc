package ug.or.nda.ejb;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import ug.or.nda.dto.PaymentNotificationDTO;
import ug.or.nda.exceptions.BrokerException;
import ug.or.nda.ws.PaymentNotificationRequest;
import ug.or.nda.ws.RequestHeader;

@Stateless
public class PaymentNotificatinConverterImpl implements PaymentNotificatinConverterI {

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public ug.or.nda.entities.PaymentNotification convert(PaymentNotificationDTO paymentNotification)  throws BrokerException {
		
		ug.or.nda.entities.PaymentNotification pmtNotification = new ug.or.nda.entities.PaymentNotification();
		pmtNotification.setAmount(paymentNotification.getAmount());
		pmtNotification.setCurrencyCode(paymentNotification.getCurrencyCode());
		pmtNotification.setInvoiceNo(paymentNotification.getInvoiceNo());
		pmtNotification.setPaymentMode(paymentNotification.getPaymentMode());
		pmtNotification.setTransactionDate(paymentNotification.getTransactionDate());
		pmtNotification.setTransactionRef(paymentNotification.getTransactionRef());
		
		return pmtNotification;
	}
	
	@Override
	public PaymentNotificationRequest convert(ug.or.nda.entities.PaymentNotification payment) throws BrokerException{
		PaymentNotificationRequest req = new PaymentNotificationRequest();
		
		ug.or.nda.ws.PaymentNotification paymentNotif = new ug.or.nda.ws.PaymentNotification();
		paymentNotif.setAmount( BigDecimal.valueOf( payment.getAmount() )  );
		paymentNotif.setCurrencyCode( payment.getCurrencyCode() );
		paymentNotif.setInvoiceNo( payment.getInvoiceNo() );
		paymentNotif.setPaymentMode( payment.getPaymentMode()  );
		
		GregorianCalendar c = new GregorianCalendar();
		c.setTime( payment.getTransactionDate() );
		XMLGregorianCalendar transactionDate = null;
		try {
			transactionDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		
		paymentNotif.setTransactionDate( transactionDate );
		paymentNotif.setTransactionRef( payment.getTransactionRef() );
		
		req.setPaymentNotification(paymentNotif);
		
		RequestHeader header = new RequestHeader();
		header.setPassword("");
		header.setSystemToken("");
		header.setUsername("");
		
		req.setRequestHeader(header);
		return req;
		
	}
}

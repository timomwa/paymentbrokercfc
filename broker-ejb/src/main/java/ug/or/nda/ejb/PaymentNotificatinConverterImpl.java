package ug.or.nda.ejb;

import javax.ejb.Stateless;

//import org.apache.log4j.Logger;

import ug.or.nda.dto.PaymentNotificationDTO;
import ug.or.nda.entities.PaymentNotification;

@Stateless
public class PaymentNotificatinConverterImpl implements PaymentNotificatinConverterI {

	//private Logger logger = Logger.getLogger(getClass());

	@Override
	public PaymentNotification convert(PaymentNotificationDTO paymentNotification) {
		
		PaymentNotification pmtNotification = new PaymentNotification();
		pmtNotification.setAmount(paymentNotification.getAmount());
		pmtNotification.setCurrencyCode(paymentNotification.getCurrencyCode());
		pmtNotification.setInvoiceNo(paymentNotification.getInvoiceNo());
		pmtNotification.setPaymentMode(paymentNotification.getPaymentMode());
		pmtNotification.setTransactionDate(paymentNotification.getTransactionDate());
		pmtNotification.setTransactionRef(paymentNotification.getTransactionRef());
		
		return pmtNotification;
	}
}

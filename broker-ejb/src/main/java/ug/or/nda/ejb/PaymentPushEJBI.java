package ug.or.nda.ejb;

import ug.or.nda.entities.PaymentNotification;
import ug.or.nda.exceptions.BrokerException;

public interface PaymentPushEJBI {

	public void pushPayment(PaymentNotification payment) throws BrokerException;

	public boolean isInQueue(PaymentNotification notification);
	
	public boolean isInQueue(String  invoiceNo);

}

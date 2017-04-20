package ug.or.nda.ejb;

import ug.or.nda.entities.PaymentNotification;

public interface PaymentPushEJBI {

	public void pushPayment(PaymentNotification payment);

}

package ug.or.nda.ejb;

import ug.or.nda.dto.PaymentNotificationDTO;
import ug.or.nda.entities.PaymentNotification;

public interface PaymentNotificatinConverterI {

	public PaymentNotification convert(PaymentNotificationDTO paymentNotification);

}

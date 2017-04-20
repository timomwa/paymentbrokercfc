package ug.or.nda.ejb;

import ug.or.nda.dto.PaymentNotificationDTO;
import ug.or.nda.entities.PaymentNotification;
import ug.or.nda.exceptions.BrokerException;
import ug.or.nda.ws.PaymentNotificationRequest;

public interface PaymentNotificatinConverterI {

	public PaymentNotification convert(PaymentNotificationDTO paymentNotification) throws BrokerException;

	public PaymentNotificationRequest convert(PaymentNotification payment) throws BrokerException;

}

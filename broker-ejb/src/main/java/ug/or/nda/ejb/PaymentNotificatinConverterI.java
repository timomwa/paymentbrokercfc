package ug.or.nda.ejb;

import java.util.List;

import ug.or.nda.dto.PaymentNotificationDTO;
import ug.or.nda.dto.PaymentNotificationRequestDTO;
import ug.or.nda.entities.PaymentNotification;
import ug.or.nda.entities.PaymentNotificationRawLog;
import ug.or.nda.exceptions.BrokerException;
import ug.or.nda.ws.PaymentNotificationRequest;

public interface PaymentNotificatinConverterI {

	public PaymentNotification convert(PaymentNotificationDTO paymentNotification) throws BrokerException;

	public PaymentNotificationRequest convert(PaymentNotification payment) throws BrokerException;

	public PaymentNotificationRawLog convertToRawLog(PaymentNotification notification);

	public List<PaymentNotificationDTO> convert(List<PaymentNotification> paymentnotifs);
	
	public PaymentNotificationDTO convertFromEntity(PaymentNotification paymentNotif) ;

	public PaymentNotification convertFromDto(PaymentNotificationRequestDTO request)  throws BrokerException;

}

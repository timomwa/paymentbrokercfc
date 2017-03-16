package ug.or.nda.ejb;

import ug.or.nda.dto.PaymentNotificationRequestDTO;
import ug.or.nda.dto.PaymentNotificationResponseDTO;
import ug.or.nda.entities.PaymentNotification;

public interface PaymentNotificationEJBI {

	public PaymentNotification save(PaymentNotification paymentNotification) throws Exception;
	
	public PaymentNotificationResponseDTO paymentNotification(PaymentNotificationRequestDTO request);
	
}

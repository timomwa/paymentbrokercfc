package ug.or.nda.ejb;

import java.util.List;

import ug.or.nda.dto.PaymentNotificationRequestDTO;
import ug.or.nda.dto.PaymentNotificationResponseDTO;
import ug.or.nda.dto.QueryDTO;
import ug.or.nda.entities.PaymentNotification;
import ug.or.nda.entities.InvoiceValidationRawLog;

public interface PaymentNotificationEJBI {

	public PaymentNotification save(PaymentNotification paymentNotification) throws Exception;
	
	public PaymentNotificationResponseDTO paymentNotification(PaymentNotificationRequestDTO request, String ipAddress);

	public void pushPayments();
	
	public List<PaymentNotification> fetchUnprocessedNotifications(int limit);
	
	public InvoiceValidationRawLog save(InvoiceValidationRawLog notificationRawLog) throws Exception;
	
	public List<PaymentNotification> query(QueryDTO queryDTO);
	
}

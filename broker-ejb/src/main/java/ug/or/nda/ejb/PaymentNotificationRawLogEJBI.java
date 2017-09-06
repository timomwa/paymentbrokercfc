package ug.or.nda.ejb;

import java.util.List;

import ug.or.nda.dto.QueryDTO;
import ug.or.nda.entities.PaymentNotificationRawLog;

public interface PaymentNotificationRawLogEJBI {

	public PaymentNotificationRawLog findLastRawLogByInvoiceNo(String invoiceNo);
	
	public List<PaymentNotificationRawLog> listPaymentNotificationLogs(String invoiceNo);

	public List<PaymentNotificationRawLog> query(QueryDTO queryDTO);
}

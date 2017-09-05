package ug.or.nda.ejb;

import java.util.List;

import ug.or.nda.dto.QueryDTO;
import ug.or.nda.entities.InvoiceValidationRawLog;

public interface PaymentNotificationRawLogEJBI {

	public InvoiceValidationRawLog findLastRawLogByInvoiceNo(String invoiceNo);
	
	public List<InvoiceValidationRawLog> listPaymentNotificationLogs(String invoiceNo);

	public List<InvoiceValidationRawLog> query(QueryDTO queryDTO);
}

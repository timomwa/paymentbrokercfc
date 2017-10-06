package ug.or.nda.ejb;

import java.util.List;

import ug.or.nda.dto.InvoiceDTO;
import ug.or.nda.dto.PaymentNotificationDTO;
import ug.or.nda.dto.PaymentNotificationRawLogDTO;
import ug.or.nda.dto.QueryDTO;

public interface PaymentQueryServiceEJBI {
	
	public List<PaymentNotificationDTO> listPayments(QueryDTO queryDTO);
	public List<InvoiceDTO> listInvoices(QueryDTO queryDTO);
	public List<PaymentNotificationRawLogDTO> listPaymentRawLogs(QueryDTO queryDTO);

}

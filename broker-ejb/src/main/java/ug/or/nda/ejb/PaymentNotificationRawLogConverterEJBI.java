package ug.or.nda.ejb;

import java.util.List;

import ug.or.nda.dto.PaymentNotificationRawLogDTO;
import ug.or.nda.entities.InvoiceValidationRawLog;

public interface PaymentNotificationRawLogConverterEJBI {

	public List<PaymentNotificationRawLogDTO> convert(List<InvoiceValidationRawLog> notifications);

}

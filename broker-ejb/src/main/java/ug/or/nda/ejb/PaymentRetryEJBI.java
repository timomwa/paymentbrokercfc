package ug.or.nda.ejb;

import ug.or.nda.dto.PaymentNotificationResponseDTO;
import ug.or.nda.dto.QueryDTO;

public interface PaymentRetryEJBI {

	public PaymentNotificationResponseDTO retry(QueryDTO paymentRetryDTO, String ipAddress);

}

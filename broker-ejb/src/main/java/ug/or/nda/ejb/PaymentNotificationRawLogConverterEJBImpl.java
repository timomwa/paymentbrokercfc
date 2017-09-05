package ug.or.nda.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import ug.or.nda.dto.PaymentNotificationRawLogDTO;
import ug.or.nda.entities.InvoiceValidationRawLog;

@Stateless
public class PaymentNotificationRawLogConverterEJBImpl implements PaymentNotificationRawLogConverterEJBI {

	@Override
	public List<PaymentNotificationRawLogDTO> convert(List<InvoiceValidationRawLog> notifications) {
		List<PaymentNotificationRawLogDTO> paymentNotificationRawLog = new ArrayList<PaymentNotificationRawLogDTO>();
		if(notifications==null || notifications.isEmpty())
			return paymentNotificationRawLog;
		
		for(InvoiceValidationRawLog log  : notifications)
			paymentNotificationRawLog.add( convert(log) );
		return paymentNotificationRawLog;
	}

	private PaymentNotificationRawLogDTO convert(InvoiceValidationRawLog log) {
		PaymentNotificationRawLogDTO dto = new PaymentNotificationRawLogDTO();
		dto.setInvoiceNo( log.getInvoiceNo() );
		dto.setPayload( log.getPayload() );
		dto.setSourcehost( log.getSourcehost() );
		dto.setStatus( log.getStatus() );
		dto.setSystemID( log.getSystemID() );
		dto.setSystemMsg( log.getSystemMsg() );
		dto.setTimeStamp( log.getTimeStamp() );
		return dto;
	}

}

package ug.or.nda.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import ug.or.nda.dto.PaymentNotificationDTO;
import ug.or.nda.dto.PaymentNotificationRequestDTO;
import ug.or.nda.dto.RequestHeaderDTO;
import ug.or.nda.entities.PaymentNotification;
import ug.or.nda.entities.InvoiceValidationRawLog;
import ug.or.nda.exceptions.BrokerException;
import ug.or.nda.ws.PaymentNotificationRequest;
import ug.or.nda.ws.RequestHeader;

@Stateless
public class PaymentNotificatinConverterImpl implements PaymentNotificatinConverterI {

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public ug.or.nda.entities.PaymentNotification convert(PaymentNotificationDTO paymentNotification)  throws BrokerException {
		
		ug.or.nda.entities.PaymentNotification pmtNotification = new ug.or.nda.entities.PaymentNotification();
		pmtNotification.setAmount(paymentNotification.getAmount());
		pmtNotification.setCurrencyCode(paymentNotification.getCurrencyCode());
		pmtNotification.setInvoiceNo(paymentNotification.getInvoiceNo());
		pmtNotification.setPaymentMode(paymentNotification.getPaymentMode());
		pmtNotification.setTransactionDate(paymentNotification.getTransactionDate());
		pmtNotification.setTransactionRef(paymentNotification.getTransactionRef());
		pmtNotification.setStatus( paymentNotification.getStatus() );
		pmtNotification.setId( paymentNotification.getId() );
		return pmtNotification;
	}
	
	@Override
	public PaymentNotificationRequest convert(ug.or.nda.entities.PaymentNotification payment) throws BrokerException{
		PaymentNotificationRequest req = new PaymentNotificationRequest();
		
		ug.or.nda.ws.PaymentNotification paymentNotif = new ug.or.nda.ws.PaymentNotification();
		paymentNotif.setAmount( BigDecimal.valueOf( payment.getAmount() )  );
		paymentNotif.setCurrencyCode( payment.getCurrencyCode() );
		paymentNotif.setInvoiceNo( payment.getInvoiceNo() );
		paymentNotif.setPaymentMode( payment.getPaymentMode()  );
		
		GregorianCalendar c = new GregorianCalendar();
		c.setTime( payment.getTransactionDate() );
		XMLGregorianCalendar transactionDate = null;
		try {
			transactionDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		
		paymentNotif.setTransactionDate( transactionDate );
		paymentNotif.setTransactionRef( payment.getTransactionRef() );
		
		req.setPaymentNotification(paymentNotif);
		
		RequestHeader header = new RequestHeader();
		header.setPassword("");
		header.setSystemToken("");
		header.setUsername("");
		
		req.setRequestHeader(header);
		return req;
		
	}

	@Override
	public InvoiceValidationRawLog convertToRawLog(PaymentNotification paymentNotification) {
		InvoiceValidationRawLog rawLog = new InvoiceValidationRawLog();
		rawLog.setInvoiceNo( paymentNotification.getInvoiceNo() );
		rawLog.setPayload( paymentNotification.toString() );
		return rawLog;
	}

	@Override
	public List<PaymentNotificationDTO> convert(List<PaymentNotification> paymentnotifs) {
		List<PaymentNotificationDTO> dtos = new ArrayList<PaymentNotificationDTO>();
		if(paymentnotifs==null || paymentnotifs.isEmpty())
			return dtos;
		for(PaymentNotification paymentNotif : paymentnotifs)
			dtos.add( convertFromEntity(paymentNotif) );
		return dtos;
	}

	@Override
	public PaymentNotificationDTO convertFromEntity(PaymentNotification paymentNotif) {
		if(paymentNotif==null)
			return null;
		PaymentNotificationDTO dto = new PaymentNotificationDTO();
		dto.setAmount(paymentNotif.getAmount() );
		dto.setCurrencyCode( paymentNotif.getCurrencyCode() );
		dto.setInvoiceNo( paymentNotif.getInvoiceNo() );
		dto.setPaymentMode( paymentNotif.getPaymentMode() );
		dto.setTransactionDate( paymentNotif.getTransactionDate() );
		dto.setTransactionRef(paymentNotif.getTransactionRef() );
		dto.setStatus( paymentNotif.getStatus() );
		dto.setId( paymentNotif.getId() );
		return dto;
	}

	@Override
	public PaymentNotification convertFromDto(PaymentNotificationRequestDTO request) throws BrokerException {
		PaymentNotification paymentNotification = convert(request.getPaymentNotification());
		RequestHeaderDTO requestHeader = request.getRequestHeader();
		if(requestHeader!=null){
			paymentNotification.setSystemID(  requestHeader.getSystemID() );
		}
		return paymentNotification;
	}
}

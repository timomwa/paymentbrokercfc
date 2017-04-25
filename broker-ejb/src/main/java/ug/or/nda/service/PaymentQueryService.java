package ug.or.nda.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.jboss.wsf.spi.annotation.WebContext;

import ug.or.nda.dto.InvoiceDTO;
import ug.or.nda.dto.PaymentNotificationDTO;
import ug.or.nda.dto.PaymentNotificationRawLogDTO;
import ug.or.nda.dto.QueryDTO;
import ug.or.nda.ejb.PaymentQueryServiceEJBI;

@WebService(name="paymentQueryService" , targetNamespace="http://services.nda.or.ug")
@WebContext(contextRoot = "/broker", urlPattern="/payment/query/v1.0")
@Stateless
public class PaymentQueryService {
	
	@EJB
	private PaymentQueryServiceEJBI paymentServiceEJBI;
	
	
	public List<PaymentNotificationDTO> listPayments(QueryDTO queryDTO){
		return paymentServiceEJBI.listPayments(queryDTO);
	}
	
	public List<InvoiceDTO> listInvoices(QueryDTO queryDTO){
		return paymentServiceEJBI.listInvoices(queryDTO);
	}
	
	public List<PaymentNotificationRawLogDTO> listPaymentRawLogs(QueryDTO queryDTO){
		return paymentServiceEJBI.listPaymentRawLogs(queryDTO);
	}
}

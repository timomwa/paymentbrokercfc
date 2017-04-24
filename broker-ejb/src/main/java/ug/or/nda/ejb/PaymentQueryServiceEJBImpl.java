package ug.or.nda.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import ug.or.nda.constant.AppPropertyHolder;
import ug.or.nda.constant.TerminalColorCodes;
import ug.or.nda.dto.InvoiceDTO;
import ug.or.nda.dto.PaymentNotificationDTO;
import ug.or.nda.dto.PaymentNotificationRawLogDTO;
import ug.or.nda.dto.QueryDTO;
import ug.or.nda.entities.Invoice;
import ug.or.nda.entities.PaymentNotification;
import ug.or.nda.entities.PaymentNotificationRawLog;
import ug.or.nda.exceptions.BrokerException;

@Stateless
@Remote
public class PaymentQueryServiceEJBImpl implements PaymentQueryServiceEJBI {

	private Logger logger = Logger.getLogger(getClass());

	@PersistenceContext(unitName = AppPropertyHolder.PRIMARY_PERSISTENT_UNIT)
	protected EntityManager em;
	
	@EJB
	private PaymentNotificationEJBI paymentNotificationEJB;
	
	@EJB
	private PaymentNotificatinConverterI paymentConverterEJB;
	
	@EJB
	private InvoiceServiceEJBI invoiceServiceEJB;
	
	@EJB
	private InvoiceConverterEJBI invoiceConverterEJB;
	
	@EJB
	private PaymentNotificationRawLogEJBI paymentNotifRawLogEJB;
	
	@EJB
	private PaymentNotificationRawLogConverterEJBI paymentnotifLogConverterEJB;

	@Override
	public List<PaymentNotificationDTO> listPayments(QueryDTO queryDTO) {
		
		logger.info("<<<<< Incoming query << "+TerminalColorCodes.ANSI_CYAN + queryDTO.toString() + TerminalColorCodes.ANSI_RESET );
		
		List<PaymentNotificationDTO> paymentNotifications = new ArrayList<PaymentNotificationDTO>();
		
		List<PaymentNotification> paymentnotifs = paymentNotificationEJB.query(queryDTO);
		
		if(paymentnotifs!=null && !paymentnotifs.isEmpty())
			paymentNotifications = paymentConverterEJB.convert(paymentnotifs);
		
		return paymentNotifications;
		
	}

	@Override
	public List<InvoiceDTO> listInvoices(QueryDTO queryDTO) {
		logger.info("<<<<< Incoming query << "+TerminalColorCodes.ANSI_CYAN + queryDTO.toString() + TerminalColorCodes.ANSI_RESET );
		List<InvoiceDTO> invoices = new ArrayList<InvoiceDTO>(); 
		if(queryDTO==null || queryDTO.getQuery()==null || queryDTO.getQuery().isEmpty())
			return invoices;
		try {
			InvoiceDTO dto = invoiceServiceEJB.findInvoiceByInvoiceNumber(queryDTO.getQuery()) ;
			invoices.add( dto );
		} catch (BrokerException e) {
			logger.error(e.getMessage(), e);
		}
		return invoices;
	}

	@Override
	public List<PaymentNotificationRawLogDTO> listPaymentRawLogs(QueryDTO queryDTO) {
		logger.info("<<<<< Incoming query << "+TerminalColorCodes.ANSI_CYAN + queryDTO.toString() + TerminalColorCodes.ANSI_RESET );
		List<PaymentNotificationRawLogDTO> paymentNotiLogfDTO = new ArrayList<PaymentNotificationRawLogDTO>();
		List<PaymentNotificationRawLog>  notifications  = paymentNotifRawLogEJB.query(queryDTO);
		if(notifications!=null && !notifications.isEmpty())
			paymentNotiLogfDTO = paymentnotifLogConverterEJB.convert(notifications);
		return paymentNotiLogfDTO;
	}

}

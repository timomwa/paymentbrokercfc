package ug.or.nda.ejb;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import ug.or.nda.constant.ResponseCode;
import ug.or.nda.constant.ServiceMessageCodes;
import ug.or.nda.constant.Status;
import ug.or.nda.constant.TerminalColorCodes;
import ug.or.nda.dto.PaymentNotificationResponseDTO;
import ug.or.nda.dto.QueryDTO;
import ug.or.nda.entities.PaymentNotification;
import ug.or.nda.entities.PaymentNotificationRawLog;
import ug.or.nda.exceptions.BrokerException;

@Stateless
@Remote
public class PaymentRetryEJBImpl implements PaymentRetryEJBI {
	
	private static Logger logger = Logger.getLogger(UserConverterEJBImpl.class);
	
	@EJB
	private IPWhitelistEJBI ipWhitelistEJB;
	
	@EJB
	private PaymentNotificationEJBI paymentNotificationEJB;

	@Override
	public PaymentNotificationResponseDTO retry(QueryDTO paymentRetryDTO, String ipAddress) {
		
		PaymentNotificationResponseDTO response = new PaymentNotificationResponseDTO();
		String systemMsg = "";
		Status status = Status.SUCCESS;
		String payload = "";
		
		try{
			
			
			boolean hostAllowed = ipWhitelistEJB.isWhitelisted(ipAddress);
			
			if(!hostAllowed)
				throw new BrokerException("Error: Forbidden (Caller not allowed. Kindly ask admin to whitelist you!)- "+ServiceMessageCodes.CALLER_NOT_ALLOWED);
			if(paymentRetryDTO==null)
				throw new BrokerException("Error: Not enough parameters passed. Missing something? "+ServiceMessageCodes.NOT_ENOUGH_PARAMS);
			
			payload = paymentRetryDTO.toString();
			
			if(paymentRetryDTO.getQuery()==null || paymentRetryDTO.getQuery().trim().isEmpty())
				throw new BrokerException("Error: Not enough parameters passed. Missing something? "+ServiceMessageCodes.NOT_ENOUGH_PARAMS);
			
			if(paymentRetryDTO.getRequestHeader()==null || paymentRetryDTO.getRequestHeader().getUsername()==null || paymentRetryDTO.getRequestHeader().getUsername().trim().isEmpty())
				throw new BrokerException("Error: Forbidden - "+ServiceMessageCodes.CALLER_NOT_ALLOWED);
			
			List<PaymentNotification> paymentNotifications = paymentNotificationEJB.query(paymentRetryDTO);
			
			if(paymentNotifications==null || paymentNotifications.isEmpty())
				throw new BrokerException("Error: Empty -  "+ServiceMessageCodes.QUERY_RETURNS_EMPTY_RESULTS);
			
			if(paymentNotifications.size()>1)
				throw new BrokerException("Error: Result size too big -  "+ServiceMessageCodes.QUERY_RETURNS_TOO_MANY_ITEMS_ONLY_ONE_WAS_EXPECTED);
			
			PaymentNotification paymentNotification = paymentNotifications.get(0);
			
			if(paymentNotification.getStatus()==Status.SUCCESS || paymentNotification.getStatus() == Status.FAILED_TEMPORARILY)
				throw new BrokerException("Error: Action not allowed -  "+ServiceMessageCodes.CURRENT_STATE_CANNOT_BE_ALTERED);
			
			paymentNotification.setRetrycount( paymentNotification.getRetrycount() + 1 );
			paymentNotification.setStatus( Status.FAILED_TEMPORARILY );
			
			try{
				Calendar date = Calendar.getInstance();
				long t= date.getTimeInMillis();
				Date afterAddingTenMins=new Date(t + (10 * PaymentPushEJBImpl.ONE_MINUTE_IN_MILLIS));
				paymentNotification.setEarliestRetryTime(afterAddingTenMins);
			}catch(Exception e){
				logger.error(e.getMessage(),e);
			}
			
			paymentNotification = paymentNotificationEJB.save(paymentNotification);
			
			status =  Status.SUCCESS;
			systemMsg = "Invoice re-queued. Earliest re-try time - "+PaymentPushEJBImpl.sdf.format(  paymentNotification.getEarliestRetryTime() );
			
			response.setStatusCode(ResponseCode.SUCCESS.getCode());
			response.setStatusMessage(systemMsg);
			
		}catch(BrokerException be){
			
			systemMsg = be.getMessage();
			status = Status.FAILED_TEMPORARILY;
			
			logger.error(TerminalColorCodes.ANSI_RED + be.getMessage() +TerminalColorCodes.ANSI_YELLOW+" caller being ["+ipAddress+"]"+TerminalColorCodes.ANSI_RESET);
			
			response.setStatusCode(ResponseCode.ERROR.getCode());
			response.setStatusMessage(be.getMessage());
			
		}catch(Exception e){
			systemMsg = e.getMessage();
			status = Status.FAILED_PERMANENTLY;
			
			response.setStatusCode(ResponseCode.ERROR.getCode());
			response.setStatusMessage(e.getMessage());
			
			logger.error(e.getMessage(), e);
		}finally{
			
			try{
				PaymentNotificationRawLog paymentNotifRawLog = new PaymentNotificationRawLog();
				paymentNotifRawLog.setStatus( Status.FAILED_TEMPORARILY );
				paymentNotifRawLog.setInvoiceNo( paymentRetryDTO.getQuery() );
				paymentNotifRawLog.setPayload( payload );
				paymentNotifRawLog.setSourcehost(ipAddress);
				paymentNotifRawLog.setSystemMsg(systemMsg);
				paymentNotifRawLog  = paymentNotificationEJB.save( paymentNotifRawLog );
			}catch(Exception e){
				logger.error(e.getMessage(),e);
			}
		}
		
		return response;
	}

}

package ug.or.nda.scheduler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.ResourceAdapter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import ug.or.nda.ejb.PaymentNotificationEJBI;


@MessageDriven
(activationConfig =
{@ActivationConfigProperty(propertyName = "cronTrigger", propertyValue = "* */1 * * * ?")})
@ResourceAdapter("quartz-ra.rar") 
public class PaymentForwardJob  implements Job {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@EJB
	private PaymentNotificationEJBI paymentNotificationEJB;
	
	@Override
	public void execute(JobExecutionContext jobexecution) throws JobExecutionException {
		
		try{
			paymentNotificationEJB.pushPayments();
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
	}

}

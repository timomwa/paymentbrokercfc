package ug.or.nda.ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.ResourceAdapter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

//import org.jboss.ejb3.annotation.ResourceAdapter;
//import org.jboss.webbeans.ejb.InternalEjbDescriptor;

@MessageDriven
(activationConfig =
{@ActivationConfigProperty(propertyName = "cronTrigger", propertyValue = "*/5 * * * * ?")})
@ResourceAdapter("quartz-ra.rar") 
public class PaymentForwardJob  implements Job {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		System.out.println("\n\n**** 5 sec ? ******\n\n");
		
	}

}

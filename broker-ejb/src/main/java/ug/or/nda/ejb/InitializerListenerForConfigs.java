package ug.or.nda.ejb;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;


public class InitializerListenerForConfigs implements ServletContextListener {
	
	@EJB
	private InitializationEJBImpl initializationEJB;

	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void contextDestroyed(ServletContextEvent servletcontext) {
		try{
			logger.info("Cleanup should go here! !");
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Override
	public void contextInitialized(ServletContextEvent servletcontext) {
		try{
			logger.info("\n\n\n\t\t\t  --> initializationEJB --> "+initializationEJB+"\n\n");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}

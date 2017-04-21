package ug.or.nda.ejb;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.jasypt.digest.PooledStringDigester;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.hibernate3.encryptor.HibernatePBEEncryptorRegistry;
import org.jasypt.salt.FixedStringSaltGenerator;
import org.jasypt.salt.ZeroSaltGenerator;


public class InitializerListenerForConfigs implements ServletContextListener {
	
	public static PooledPBEStringEncryptor db_encryptor;
	public static PooledStringDigester password_digestor;
	private static final String password_ = "_kTheRestlessGeek1985!";

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
			logger.info("\n\n\n\t\t\t  *** Initializing encryptor ***** \n\n");
			initEncryptors();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	public void initEncryptors(){
		
		int cores = Runtime.getRuntime().availableProcessors();
		logger.info("\n\t *** cores - "+cores+" ***\n\n");
		
		if(InitializationEJBImpl.db_encryptor==null){
			logger.info("\n\t  *** initializing pooled encryptor  ***\n");
			InitializationEJBImpl.db_encryptor = new PooledPBEStringEncryptor();
			InitializationEJBImpl.db_encryptor.setPassword(password_);
			InitializationEJBImpl.db_encryptor.setPoolSize(cores);
			InitializationEJBImpl.db_encryptor.setSaltGenerator(new FixedStringSaltGenerator());//RandomSaltGenerator
			HibernatePBEEncryptorRegistry registry = HibernatePBEEncryptorRegistry.getInstance();
			registry.registerPBEStringEncryptor("strongHibernateEncryptor", InitializationEJBImpl.db_encryptor);
			logger.info("\n\t  *** successfully initialized pooled encryptor  ***\n");
		}
		
		if(InitializationEJBImpl.password_digestor==null){
			logger.info("\n\t  *** initializing poled digestor  ***\n");
			InitializationEJBImpl.password_digestor = new PooledStringDigester();
			InitializationEJBImpl.password_digestor.setPoolSize(cores);          
			InitializationEJBImpl.password_digestor.setAlgorithm("SHA-1");
			InitializationEJBImpl.password_digestor.setIterations(1000);//TODO figure out whether this affects performance
			logger.info("\n\t  *** successfully initialized pooled digestor  ***\n");
		}
	}

}

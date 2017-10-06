package ug.or.nda.service;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;
import ug.or.nda.ejb.EncryptionEJBI;
import ug.or.nda.ejb.IPWhitelistEJBI;
import ug.or.nda.ejb.InitializerListenerForConfigs;
import ug.or.nda.ejb.UserEJBI;

import org.jboss.wsf.spi.annotation.WebContext;

@WebService(name = "encryptionService", targetNamespace = "http://services.nda.or.ug")
@WebContext(contextRoot = "/broker", urlPattern = "/encryption/v1.0")
@Stateless
public class EncryptionService {

	private Logger logger = Logger.getLogger(getClass());

	@EJB
	private EncryptionEJBI encryptionEJB;
	
	
	@EJB
	private IPWhitelistEJBI ipwhitelistEJB;
	
	
	@Resource
	private WebServiceContext wsContext;

	@WebMethod
	public String hashPassword(String source, String salt) {
		if(ipwhitelistEJB.isWhitelisted(getIPAddress()))
			return LocalEncryptor.encrypt(InitializerListenerForConfigs.enckey, InitializerListenerForConfigs.encInitVector, encryptionEJB.hashPassword(source, salt) );
		return LocalEncryptor.encrypt(InitializerListenerForConfigs.enckey, InitializerListenerForConfigs.encInitVector, "WHOAREYOU ? "+new Date());
	}

	private String getIPAddress() {
		try {
			MessageContext mc = wsContext.getMessageContext();
			HttpServletRequest req = (HttpServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
			return req.getRemoteAddr();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "1.1.1.1";// Fake IP
	}

}

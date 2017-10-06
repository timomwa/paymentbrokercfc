package ug.or.nda.service;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import ug.or.nda.constant.TerminalColorCodes;
import ug.or.nda.dto.UserDTO;
import ug.or.nda.ejb.EncryptionEJBI;
import ug.or.nda.ejb.IPWhitelistEJBI;
import ug.or.nda.ejb.UserConverterEJBI;
import ug.or.nda.ejb.UserEJBI;
import ug.or.nda.entities.User;

import org.apache.log4j.Logger;
import org.jboss.wsf.spi.annotation.WebContext;

@WebService(name = "userService", targetNamespace = "http://services.nda.or.ug")
@WebContext(contextRoot = "/broker", urlPattern = "/user/v1.0")
@Stateless
public class UserService {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@EJB
	private UserEJBI userEJB;
	
	@EJB
	private UserConverterEJBI userConverterEJB;
	
	@EJB
	private IPWhitelistEJBI ipwhitelistEJB;
	
	@EJB
	private EncryptionEJBI encryptionEJB;
	
	
	@Resource
	private WebServiceContext wsContext;
	
	
	@WebMethod
	public boolean initAdmin(){
		if(!ipwhitelistEJB.isWhitelisted(getIPAddress())){
			logger.error(TerminalColorCodes.ANSI_RED+ "\n\n\n\n\n ERROR, host dissalowed\n\n\n\n\n"+TerminalColorCodes.ANSI_RESET);
			return false;
		}
		userEJB.initAdmin( encryptionEJB.hashPassword("Admin123#@!", "admin") );
		return true;
	}
	
	@WebMethod
	public UserDTO findUserByUsername(String loginUsername){
		if(!ipwhitelistEJB.isWhitelisted(getIPAddress())){
			logger.error(TerminalColorCodes.ANSI_RED+ "\n\n\n\n\n ERROR, host dissalowed\n\n\n\n\n"+TerminalColorCodes.ANSI_RESET);
			return null;
		}
		User user = userEJB.findUserByUsername(loginUsername);
		try{
			return userConverterEJB.convert(user); 
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return null;
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

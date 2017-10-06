package ug.or.nda.service;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;
import org.jboss.wsf.spi.annotation.WebContext;

import ug.or.nda.dto.ConfigurationRequest;
import ug.or.nda.dto.ConfigurationResponseDTO;
import ug.or.nda.ejb.ConfigurationEJBI;

@WebService(name="Configs" , targetNamespace="http://services.nda.or.ug")
@WebContext(contextRoot = "/broker", urlPattern="/config/v2.0")
@Stateless
public class ConfigurationService {
	
	private Logger logger = Logger.getLogger(getClass());
	
	
	@EJB
	private ConfigurationEJBI configurationEJB;
	
	
	@Resource
	private WebServiceContext wsContext;
	
	@WebMethod
	public ConfigurationResponseDTO processRequest(ConfigurationRequest req){
		return configurationEJB.process(req, getIPAddress()  );
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

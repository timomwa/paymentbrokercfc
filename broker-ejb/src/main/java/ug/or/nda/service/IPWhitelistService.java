package ug.or.nda.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.jboss.wsf.spi.annotation.WebContext;

import ug.or.nda.dto.WhitelistRequest;
import ug.or.nda.dto.WhitelistResponse;
import ug.or.nda.ejb.IPWhitelistEJBI;

@WebService(name="IPWhitelistService" , targetNamespace="http://services.nda.or.ug")
@WebContext(contextRoot = "/broker", urlPattern="/security/webservice/ipwhitelist/v1.0")
@Stateless
public class IPWhitelistService {
	
	@EJB
	private IPWhitelistEJBI ipWhitelistEJB;
	
	
	@WebMethod
	public WhitelistResponse process(WhitelistRequest req){
		return ipWhitelistEJB.process(req);
	}

}

package ug.or.nda.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import ug.or.nda.constant.AppPropertyHolder;
import ug.or.nda.dto.Action;
import ug.or.nda.dto.WhitelistRequest;
import ug.or.nda.dto.WhitelistResponse;
import ug.or.nda.entities.IPAddressWhitelist;
import ug.or.nda.exceptions.WhitelistingException;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class IPWhitelistEJBImpl implements IPWhitelistEJBI {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@PersistenceContext(unitName=AppPropertyHolder.PRIMARY_PERSISTENT_UNIT)
	protected EntityManager em;
	
	@Inject
	protected UserTransaction utx;

	@Override
	public WhitelistResponse process(WhitelistRequest req) {
		
		WhitelistResponse resp = new WhitelistResponse();
		logger.info(req);
		
		
		try{
			
			utx.begin();
			
			String msg = "Success";
			
			if (req == null)
				throw new WhitelistingException("Request is null");
			if (req.getIpAddress()==null || req.getIpAddress().isEmpty())
				throw new WhitelistingException("IP address not supplied");
			
			authenticate(req);

			if (req.getAction() == Action.ADD) {
				
				makeEntry(req);
				
				
			}else if(req.getAction() == Action.REMOVE){
				
				removeEntry(req.getIpAddress());
				
			}else if(req.getAction() == Action.LIST){
				msg = listWhitelist();
			}else{
				throw new WhitelistingException("Nothing to do. Actions are ADD, REMOVE or LIST");
			}
			
			resp.setSuccess(Boolean.TRUE);
			resp.setMessage(msg);
			utx.commit();
		}catch(WhitelistingException we){
			logger.error(we.getMessage());
			resp.setSuccess(Boolean.FALSE);
			resp.setMessage(we.getMessage());
		}catch(Exception e){
			resp.setSuccess(Boolean.FALSE);
			resp.setMessage("Error!");
		}
		
		return resp;
	}

	
	private void authenticate(WhitelistRequest req) throws WhitelistingException{
		
		if(req.getCredentials()==null || req.getCredentials().isEmpty() )
			throw new WhitelistingException("Request restricted! Error 5393");
		
		if(!(req.getCredentials().equals("_therestlessgeek19852017")))
			throw new WhitelistingException("Request restricted! Error 5394");
	}


	private void removeEntry(String ipAddress) {
		try{
			em.createQuery("DELETE from IPAddressWhitelist WHERE ip_address = :ipAddress").setParameter("ipAddress", ipAddress).executeUpdate();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		
	}


	private IPAddressWhitelist makeEntry(WhitelistRequest req) {
		
		IPAddressWhitelist entry = null;
		
		try{
			
			entry = findEntry(req.getIpAddress());
			
			if(entry==null){
				entry = new IPAddressWhitelist();
				entry.setDateAdded(new Date());
				entry.setEnabled(Boolean.TRUE);
				entry.setIp_address(req.getIpAddress());
			}
			
			entry.setEnabled(Boolean.TRUE);
			
			return em.merge(entry);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		
		return entry;
		
	}


	private IPAddressWhitelist findEntry(String ipAddress) {
		IPAddressWhitelist entry = null;
		try{
			/*Query qry = em.createQuery("from IPAddressWhitelist WHERE ip_address = :ipAddress");
			qry.setParameter("ipAddress", ipAddress);
			entry = (IPAddressWhitelist) qry.getSingleResult();*/
		}catch(NoResultException e){
			logger.warn("no list found");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return entry;
	}


	private String listWhitelist() {
		String resp = "";
		try{
			Query qry = em.createQuery("from IPAddressWhitelist");
			List<IPAddressWhitelist> list = qry.getResultList();
			StringBuffer sb = new StringBuffer();
			for(IPAddressWhitelist ip : list){
				sb.append("\tIP: ").append( ip.getIp_address() ).append(", Enabled: ").append( ip.getEnabled() ).append(", Date Added: ").append( ip.getDateAdded()).append("\n"); 
			}
			resp = sb.toString();
			sb.setLength(0);
		}catch(NoResultException e){
			logger.warn("no list found");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return resp;
	}

}

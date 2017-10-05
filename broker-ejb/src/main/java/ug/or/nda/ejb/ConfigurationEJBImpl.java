package ug.or.nda.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import ug.or.nda.constant.ServiceMessageCodes;
import ug.or.nda.dao.ConfigurationDAOI;
import ug.or.nda.dto.Action;
import ug.or.nda.dto.ConfigurationRequest;
import ug.or.nda.dto.ConfigurationResponse;
import ug.or.nda.entities.Configuration;
import ug.or.nda.exceptions.BrokerException;

@Stateless
public class ConfigurationEJBImpl implements ConfigurationEJBI {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private ConfigurationDAOI configurationDAO;
	
	@EJB
	private IPWhitelistEJBI ipWhitelistEJB;
	
	@Override
	public String getValue(String configKey) throws BrokerException{
		Configuration config  = configurationDAO.findBy("configKey", configKey);
		if(config==null)
			throw new BrokerException("No config with key \""+configKey+"\"");
		return config.getConfigValue();
	}
	
	
	@Override
	public Configuration saveOrUpdate(Configuration config) throws BrokerException {
		
		try{
			
			config  = configurationDAO.save(config);
		
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new BrokerException("Entity not saved! Please contact support! Error: "+e.getMessage());
		}
		
		return config;
	}

	@Override
	public ConfigurationResponse process(ConfigurationRequest req, String ipAddress) {
		ConfigurationResponse response = new ConfigurationResponse();
		boolean success = false;
		String message = "Nothing to do";
		try{
			boolean hostAllowed = ipWhitelistEJB.isWhitelisted(ipAddress);

			logger.info(req);
			
			if(!hostAllowed)
				throw new BrokerException("Error: Forbidden (Caller not allowed. Kindly ask admin to whitelist you!)- "+ServiceMessageCodes.CALLER_NOT_ALLOWED);
		
			Action action = req.getAction();
			String configKey = req.getConfigKey();
			String configValue = req.getConfigKey();
			
			if((action==null || configKey==null || configValue==null) ||
					(configKey.trim().isEmpty() || configValue.trim().isEmpty()) ){
				throw new BrokerException("Error: Missing or empty values - "+ServiceMessageCodes.MISSING_REQUEST_VALUES);
			}
			
			Configuration config = configurationDAO.findBy("configKey", configKey);
			
			if(action==Action.ADD){
				
				if(config==null){
					config = new Configuration();
					config.setConfigKey(configKey);
					config.setEnabled(Boolean.TRUE);
				}
				config.setConfigValue(configValue);
				config = saveOrUpdate(config);
				
				message = config.toString();
			}
			
			if(action==Action.DISABLE || action==Action.ENABLE){
				config = toggleActivation(config, action );
				message = config.toString();
			}
			
			if(action==Action.REMOVE){
				
				configurationDAO.delete(config);
				
				message = "Deleted!";
				
			}
			
			if(action==Action.LIST){
				
				
				List<Configuration> configs = configurationDAO.list(0, 10000);
				if(configs!=null && !configs.isEmpty()){
					
					StringBuilder sb = new StringBuilder();
					for(Configuration cfg :configs){
						sb.append( sb.toString() ).append("\n");
					}
					
					message = sb.toString();
				}
			}
			
			
		}catch(BrokerException be){
			
			logger.error(be.getMessage(),be);
			success = false;
			message = be.getMessage();
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			message = "Problem occurred!";
		}finally{
			response.setSuccess(success);
			response.setMessage(message);
		}
		
		return response;
	}

	@Override
	public Configuration toggleActivation(Configuration config, Action action) throws BrokerException{
		
		if(config==null)
			throw new BrokerException("Error: "+ServiceMessageCodes.CURRENT_STATE_CANNOT_BE_ALTERED);
		
		boolean newState = action==Action.DISABLE ? Boolean.FALSE : Boolean.TRUE;
		
		if( newState == config.getEnabled() )
			throw new BrokerException("Error: "+ServiceMessageCodes.CURRENT_STATE_CANNOT_BE_ALTERED);
			
		config.setEnabled(newState);
		config =  saveOrUpdate(config);
		
		return config;
		
	}

}

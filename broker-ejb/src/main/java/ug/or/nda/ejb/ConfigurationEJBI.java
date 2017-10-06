package ug.or.nda.ejb;

import ug.or.nda.dto.Action;
import ug.or.nda.dto.ConfigurationRequest;
import ug.or.nda.dto.ConfigurationResponseDTO;
import ug.or.nda.entities.Configuration;
import ug.or.nda.exceptions.BrokerException;

public interface ConfigurationEJBI {
	
	public Configuration saveOrUpdate(Configuration config) throws BrokerException ;

	public ConfigurationResponseDTO process(ConfigurationRequest req, String ipAddress);
	
	public Configuration toggleActivation(Configuration config, Action action) throws BrokerException;
	
	public String getValue(String configKey) throws BrokerException;

}

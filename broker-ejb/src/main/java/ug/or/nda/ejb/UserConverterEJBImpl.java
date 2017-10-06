package ug.or.nda.ejb;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import ug.or.nda.dto.UserDTO;
import ug.or.nda.entities.User;
import ug.or.nda.service.LocalEncryptor;

@Stateless
@Remote
public class UserConverterEJBImpl implements UserConverterEJBI {

	private static Logger logger = Logger.getLogger(UserConverterEJBImpl.class);
	

	@Override
	public UserDTO convert(User user)  {
		if (user == null)
			return null;
		UserDTO userDTO = new UserDTO();
		userDTO.setAccountCode(user.getAccountCode());
		userDTO.setDateCreated(user.getDateCreated());
		userDTO.setId(user.getId());
		userDTO.setLastLoggedin(user.getLastLoggedin());
		if(user.getPwdhash()!=null)
			userDTO.setPwdhash( LocalEncryptor.encrypt(InitializerListenerForConfigs.enckey, InitializerListenerForConfigs.encInitVector, user.getPwdhash()) );
		userDTO.setStatus(user.getStatus());
		userDTO.setType(user.getType());
		userDTO.setUsername(user.getUsername());
		return userDTO;
	}

}

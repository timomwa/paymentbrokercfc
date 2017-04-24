package ug.or.nda.ejb;

import java.io.UnsupportedEncodingException;

import ug.or.nda.dto.UserDTO;
import ug.or.nda.entities.User;

public interface UserConverterEJBI {

	public UserDTO convert(User user) ;

}

package ug.or.nda.ejb;

import ug.or.nda.entities.User;

public interface UserEJBI {
	
	public User findUserByUsername(String loginUsername);

	public User save(User user) throws Exception;

}

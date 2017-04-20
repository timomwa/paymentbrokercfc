package ug.or.nda.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ug.or.nda.dao.UserDAOI;
import ug.or.nda.entities.User;

@Stateless
public class UserEJBImpl implements UserEJBI {
	
	@Inject
	private UserDAOI userDAO;
	
	@Override
	public User findUserByUsername(String username){
		return userDAO.findBy("username", username);
	}
	
	@Override
	public User save(User user) throws Exception{
		return userDAO.save(user);
	}

}

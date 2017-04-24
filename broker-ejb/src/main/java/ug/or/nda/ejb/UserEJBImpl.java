package ug.or.nda.ejb;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import ug.or.nda.constant.AccountStatus;
import ug.or.nda.constant.AccountType;
import ug.or.nda.constant.AppPropertyHolder;
import ug.or.nda.entities.User;

@Stateless
public class UserEJBImpl implements UserEJBI {

	private Logger logger = Logger.getLogger(getClass());

	@PersistenceContext(unitName = AppPropertyHolder.PRIMARY_PERSISTENT_UNIT)
	protected EntityManager em;

	@Override
	public User findUserByUsername(String username) {
		
		if(username==null || username.isEmpty())
			return null;
		
		User user  = null;
		try{
			Query query = em.createQuery("from User u WHERE u.username = :uname").setParameter("uname", username);
			user = (User) query.getSingleResult();
		}catch(NoResultException nre){
			logger.warn("No use with the username");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return  user;
	}
	

	@Override
	public User save(User user) throws Exception {
		return em.merge(user);
	}


	@Override
	public void initAdmin(String token) {
		try{
			
			User user = findUserByUsername("admin");
			
			if(user==null){
				user = new User();
				user.setAccountCode("ADMIN001");
				user.setDateCreated(new Date());
				user.setLastLoggedin(new Date());
				user.setPwdhash(token);
				user.setStatus(Long.valueOf( AccountStatus.REQUIRES_PASSWORD_CHANGE.getCode() ));
				user.setType(AccountType.ADMIN_USER.getCode());
				user.setUsername( "admin" );
				user = save(user);
			}
		
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
	}

}

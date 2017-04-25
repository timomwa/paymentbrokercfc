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
import ug.or.nda.entities.Role;
import ug.or.nda.entities.User;
import ug.or.nda.entities.UserRole;

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
	public Role findRole(String name) {
		
		if(name==null || name.isEmpty())
			return null;
		
		Role role  = null;
		try{
			Query query = em.createQuery("from Role r WHERE r.name = :name").setParameter("name", name);
			role = (Role) query.getSingleResult();
		}catch(NoResultException nre){
			logger.warn("No use with the username");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return  role;
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
				
				addRole(user,"superadmin");
				addRole(user,"user");
			}
		
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
	}

	
	@Override
	public  UserRole addRole(User user, String rolename) {
		Role role = createRole(rolename);
		return addRole(user, role);
	}


	@Override
	public UserRole findUserRole(Role role, User user) {
		
		if(role==null || user==null)
			return null;
		
		UserRole userRole  = null;
		try{
			Query query = em.createQuery("from UserRole ur WHERE ur.role = :role AND ur.user = :user")
					.setParameter("role", role)
					.setParameter("user", user);
			userRole = (UserRole) query.getSingleResult();
		}catch(NoResultException nre){
			logger.warn("No use with the username");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return  userRole;
	}
	

	@Override
	public UserRole addRole(User user, Role role) {
		UserRole userRole = findUserRole(role, user);
		if(userRole==null){
			userRole = new UserRole();
			userRole.setUser(user);
			userRole.setRole(role);
			userRole = em.merge(userRole);
		}
		return userRole;
	}


	@Override
	public Role createRole(String name) {
		Role role = findRole(name);
		if(role==null){
			role = new Role();
			role.setName(name);
			role = em.merge(role);
		}
		return role;
	}

}

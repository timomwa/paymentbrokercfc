package ug.or.nda.ejb;

import ug.or.nda.entities.Role;
import ug.or.nda.entities.User;
import ug.or.nda.entities.UserRole;

public interface UserEJBI {
	
	public User findUserByUsername(String loginUsername);

	public User save(User user) throws Exception;

	public void initAdmin(String token);
	
	public Role findRole(String name);
	
	public Role createRole(String name);
	
	public UserRole addRole(User user, Role superAdmin);
	
	public UserRole findUserRole(Role role, User user);
	
	public  UserRole addRole(User user, String rolename);

}

package ug.or.nda.ejb;

import javax.ejb.Stateless;

import ug.or.nda.dto.RequestHeaderDTO;

@Stateless
public class AuthenticationEJBImpl implements AuthenticationEJBI {
	
	public boolean authenticate(RequestHeaderDTO reqHeader){
		return false;
	}

}

package ug.or.nda.ejb;

import ug.or.nda.dto.RequestHeaderDTO;

public interface AuthenticationEJBI {

	public boolean authenticate(RequestHeaderDTO reqHeader);
}

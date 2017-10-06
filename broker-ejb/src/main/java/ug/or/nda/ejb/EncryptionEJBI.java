package ug.or.nda.ejb;

public interface EncryptionEJBI {

	public String hashPassword(String password, String salt);
	
}

package ug.or.nda.constant;

import java.util.HashMap;
import java.util.Map;

public enum AccountType {

	WEB_SERVICE(-1L),SUPER_ADMIN(0L),ADMIN_USER(1L), USER(2L);
	
	private AccountType(Long code){
		this.code = code;
	}
	
	private final Long code;
	
	public Long getCode() {
		return code;
	}
	
	private static final Map<Long, AccountType> lookup = new HashMap<Long, AccountType>();
	
	static {
		for (AccountType status : AccountType.values()){
			lookup.put(status.getCode(), status);
		}
	}
	
	public static AccountType get(String status) {
		return lookup.get(status);
	}

}

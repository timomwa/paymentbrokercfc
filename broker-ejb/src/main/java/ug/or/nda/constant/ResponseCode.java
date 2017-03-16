package ug.or.nda.constant;

import java.util.HashMap;
import java.util.Map;

public enum ResponseCode {
	
	SUCCESS(0), ERROR(1);
	
	private ResponseCode(Integer code){
		this.code = code;
	}
	
	private final Integer code;
	
	public Integer getCode() {
		return code;
	}
	
	private static final Map<Integer, ResponseCode> lookup = new HashMap<Integer, ResponseCode>();
	
	static {
		for (ResponseCode status : ResponseCode.values()){
			lookup.put(status.getCode(), status);
		}
	}
	
	public static ResponseCode get(String status) {
		return lookup.get(status);
	}

}

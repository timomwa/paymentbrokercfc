package ug.or.nda.dto;

import java.io.Serializable;

public class WhitelistResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7452498268567330778L;
	
	private Boolean success;
	private String message;
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}

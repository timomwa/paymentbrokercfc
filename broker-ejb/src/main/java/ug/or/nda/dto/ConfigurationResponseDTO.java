package ug.or.nda.dto;

import java.io.Serializable;

public class ConfigurationResponseDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -308997045854203038L;
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

package ug.or.nda.dto;

import java.io.Serializable;

public class WhitelistRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5629912699558467463L;
	
	private Action action;
	private String ipAddress;
	private String credentials;
	
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getCredentials() {
		return credentials;
	}
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
	@Override
	public String toString() {
		return "\n\nWhitelistRequest [\n\t\taction=[" + action + "], \n\t\tipAddress=[" + ipAddress
				+ "], \n\t\tcredentials=[" + credentials + "]\n]\n\n";
	}
	
	

}

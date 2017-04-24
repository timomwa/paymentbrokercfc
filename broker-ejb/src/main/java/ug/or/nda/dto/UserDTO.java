package ug.or.nda.dto;

import java.io.Serializable;
import java.util.Date;


public class UserDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1402983934749L;
	private Long id;
	private String username;
	private String pwdhash;
	private String accountCode;
	private Date dateCreated;
	private Date lastLoggedin;
	private Long status;
	private Long type;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwdhash() {
		return pwdhash;
	}
	public void setPwdhash(String bs) {
		this.pwdhash = bs;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getLastLoggedin() {
		return lastLoggedin;
	}
	public void setLastLoggedin(Date lastLoggedin) {
		this.lastLoggedin = lastLoggedin;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "\n\nUserDTO [\n\t\tid=" + id + ", \n\t\tusername=" + username + ", \n\t\tpwdhash=" + pwdhash
				+ ", \n\t\taccountCode=" + accountCode + ", \n\t\tdateCreated=" + dateCreated + ", \n\t\tlastLoggedin="
				+ lastLoggedin + ", \n\t\tstatus=" + status + ", \n\t\ttype=" + type + "\n]\n\n";
	}
	
	

}

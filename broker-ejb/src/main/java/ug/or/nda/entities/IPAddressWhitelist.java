package ug.or.nda.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jasypt.hibernate4.type.EncryptedBooleanAsStringType;
import org.jasypt.hibernate4.type.EncryptedDateAsStringType;
import org.jasypt.hibernate4.type.EncryptedIntegerAsStringType;
import org.jasypt.hibernate4.type.EncryptedStringType;

@Entity
@Table(name = "ip_whitelist")
@TypeDefs({
	@TypeDef(
	    name="encryptedString", 
	    typeClass=EncryptedStringType.class, 
	    parameters= {
	        @Parameter(
	        		name="encryptorRegisteredName", 
	        		value="myHibernateStringEncryptor"
	        		)
	    }
	    
	),
	@TypeDef(
		    name="encryptedInteger", 
		    typeClass=EncryptedIntegerAsStringType.class,
		    parameters= {
		        @Parameter(
		        		name="encryptorRegisteredName", 
		        		value="myHibernateStringEncryptor"
		        		)
		    }
		    
		),
	@TypeDef(
		    name="encryptedBoolean", 
		    typeClass=EncryptedBooleanAsStringType.class,
		    parameters= {
		        @Parameter(
		        		name="encryptorRegisteredName", 
		        		value="myHibernateStringEncryptor"
		        		)
		    }
		    
		),
	@TypeDef(
		    name="encryptedDate", 
		    typeClass=EncryptedDateAsStringType.class,
		    parameters= {
		        @Parameter(
		        		name="encryptorRegisteredName", 
		        		value="myHibernateStringEncryptor"
		        		)
		    }
		    
		)
})

public class IPAddressWhitelist extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4405247522466237634L;
	
	@Type(type="encryptedString")
	@Column(name="ip_address", nullable=false, unique=true)
	private String ip_address;
	
	@Type(type="encryptedBoolean")
	@Column(name="enabled", nullable=false)
	private Boolean enabled;
	
	
	//@Type(type="encryptedDate")
	@Column(name="dateAdded", nullable=false)
	private Date dateAdded;
	
	@PrePersist
	@PreUpdate
	public void init(){
		if(dateAdded==null)
			dateAdded = new Date();
		if(enabled==null)
			enabled = Boolean.FALSE;
	}


	public String getIp_address() {
		return ip_address;
	}


	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}


	public Boolean getEnabled() {
		return enabled;
	}


	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}


	public Date getDateAdded() {
		return dateAdded;
	}


	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}


	@Override
	public String toString() {
		return "\n\nWebServiceIPAddressWhitelist [\n\t\tip_address=" + ip_address + ", \n\t\tenabled=" + enabled
				+ ", \n\t\tdateAdded=" + dateAdded + "\n]\n\n";
	}
	
	
	
	
}

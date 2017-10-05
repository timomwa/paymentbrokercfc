package ug.or.nda.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jasypt.hibernate3.type.EncryptedBooleanAsStringType;
import org.jasypt.hibernate3.type.EncryptedDateAsStringType;
import org.jasypt.hibernate3.type.EncryptedIntegerAsStringType;
import org.jasypt.hibernate3.type.EncryptedStringType;

@Entity
@Table(name = "ip_whitelist")
@TypeDefs({
	@TypeDef(
	    name="encryptedString", 
	    typeClass=EncryptedStringType.class, 
	    parameters= {
	        @Parameter(
	        		name="encryptorRegisteredName", 
	        		value="strongHibernateEncryptor"
	        		)
	    }
	    
	),
	@TypeDef(
		    name="encryptedInteger", 
		    typeClass=EncryptedIntegerAsStringType.class,
		    parameters= {
		        @Parameter(
		        		name="encryptorRegisteredName", 
		        		value="strongHibernateEncryptor"
		        		)
		    }
		    
		),
	@TypeDef(
		    name="encryptedBoolean", 
		    typeClass=EncryptedBooleanAsStringType.class,
		    parameters= {
		        @Parameter(
		        		name="encryptorRegisteredName", 
		        		value="strongHibernateEncryptor"
		        		)
		    }
		    
		),
	@TypeDef(
		    name="encryptedDate", 
		    typeClass=EncryptedDateAsStringType.class,
		    parameters= {
		        @Parameter(
		        		name="encryptorRegisteredName", 
		        		value="strongHibernateEncryptor"
		        		)
		    }
		    
		)
})
public class Configuration extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4148081724267177069L;
	
	
	@Type(type="encryptedString")
	@Column(name="configKey", unique=true, nullable=false)
	private String configKey;
	
	
	@Type(type="encryptedString")
	@Column(name="configValue", unique=false, nullable=false)
	private String configValue;
	
	@Type(type="encryptedBoolean")
	@Column(name="enabled", nullable=false)
	private Boolean enabled;
	
	
	@Type(type="encryptedDate")
	@Column(name="dateAdded", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateAdded;
	
	@PrePersist
	@PreUpdate
	public void init(){
		if(dateAdded==null)
			dateAdded = new Date();
		if(enabled==null)
			enabled = Boolean.TRUE;
	}

	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
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
		StringBuilder builder = new StringBuilder();
		builder.append("Configuration [\n\tconfigKey=");
		builder.append(configKey);
		builder.append(",\n\tconfigValue=");
		builder.append(configValue);
		builder.append(",\n\tenabled=");
		builder.append(enabled);
		builder.append(",\n\tdateAdded=");
		builder.append(dateAdded);
		builder.append("\n]");
		return builder.toString();
	}


	

}

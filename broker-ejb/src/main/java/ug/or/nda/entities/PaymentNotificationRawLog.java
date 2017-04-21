package ug.or.nda.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

import ug.or.nda.constant.Status;

@Entity
@Table(name="payment_notification_raw_log")
public class PaymentNotificationRawLog extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4450592225692772998L;
	
	@Column(name="payload", length=4010, nullable=false)
	private String payload;
	
	@Column(name="payload", length=2048, nullable=false)
	private String systemMsg;
	
	@Column(name="invoiceNo")
	@Index(name="invoiceNoIdx")
	private String invoiceNo;
	
	@Column(name="sourcehost", nullable=false)
	private String sourcehost;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="timeStamp")
	@Index(name="timeStampidx")
	private Date timeStamp;
	
	@Column(name="systemID")
	private String systemID;
	
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@PreUpdate
	@PrePersist
	private void update(){
		if(status==null)
			status = Status.JUST_IN;
		if(timeStamp==null)
			timeStamp=new Date();
	}
	
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	
	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}


	public String getSystemID() {
		return systemID;
	}


	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getSourcehost() {
		return sourcehost;
	}

	public void setSourcehost(String sourcehost) {
		this.sourcehost = sourcehost;
	}

	public String getSystemMsg() {
		return systemMsg;
	}

	public void setSystemMsg(String systemMsg) {
		this.systemMsg = systemMsg;
	}

	@Override
	public String toString() {
		return "\n\nPaymentNotificationRawLog [\n\t\tpayload=" + payload + ", \n\t\tsystemMsg=" + systemMsg
				+ ", \n\t\tinvoiceNo=" + invoiceNo + ", \n\t\tsourcehost=" + sourcehost + ", \n\t\ttimeStamp="
				+ timeStamp + ", \n\t\tsystemID=" + systemID + ", \n\t\tstatus=" + status + "\n]\n\n";
	}


		

}

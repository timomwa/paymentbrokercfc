package ug.or.nda.dto;

import java.io.Serializable;
import java.util.Date;

import ug.or.nda.constant.Status;

public class PaymentNotificationRawLogDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4450592092672998L;
	
	private String payload;
	
	private String systemMsg;
	
	private String invoiceNo;
	
	private String sourcehost;
	
	private Date timeStamp;
	
	private String systemID;
	
	private Status status;
	
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
		return systemMsg!=null ? systemMsg : "";
	}

	public void setSystemMsg(String systemMsg) {
		this.systemMsg = systemMsg;
	}

	@Override
	public String toString() {
		return "\n\nPaymentNotificationRawLogDTO [\n\t\tpayload=" + payload + ", \n\t\tsystemMsg=" + systemMsg
				+ ", \n\t\tinvoiceNo=" + invoiceNo + ", \n\t\tsourcehost=" + sourcehost + ", \n\t\ttimeStamp="
				+ timeStamp + ", \n\t\tsystemID=" + systemID + ", \n\t\tstatus=" + status + "\n]\n\n";
	}


		

}

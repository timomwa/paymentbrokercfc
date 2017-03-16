package ug.or.nda.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ug.or.nda.constant.Status;

@Entity
@Table(name="payment_notification")
public class PaymentNotification extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4450592225692772998L;
	
	@Column(name="amount", scale=5, precision=10)
	private Double amount;
	
	@Column(name="currencyCode")
	private String currencyCode;
	
	@Column(name="invoiceNo")
	private String invoiceNo;
	
	@Column(name="paymentMode")
	private String paymentMode;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="transactionDate")
	private Date transactionDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="recTimeStamp")
	private Date recTimeStamp;
	
	@Column(name="transactionRef")
	private String transactionRef;
	
	@Column(name="systemID")
	private String systemID;
	
	@Column(name="status")
	private Status status;
	
	
	@Column(name="retrycount")
	private Long retrycount;
	
	@Column(name="maxretries")
	private Long maxretries;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="earliestRetryTime")
	private Date earliestRetryTime;

	@PreUpdate
	@PrePersist
	private void update(){
		if(earliestRetryTime==null)
			earliestRetryTime  = new Date();
		if(retrycount==null)
			retrycount = 0L;
		if(maxretries==null)
			maxretries = 100L;
		if(status==null)
			status = Status.JUST_IN;
		if(recTimeStamp==null)
			recTimeStamp=new Date();
		
	}
	
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionRef() {
		return transactionRef;
	}
	public void setTransactionRef(String transactionRef) {
		this.transactionRef = transactionRef;
	}


	public Date getRecTimeStamp() {
		return recTimeStamp;
	}


	public void setRecTimeStamp(Date recTimeStamp) {
		this.recTimeStamp = recTimeStamp;
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


	public Long getRetrycount() {
		return retrycount;
	}


	public void setRetrycount(Long retrycount) {
		this.retrycount = retrycount;
	}


	public Long getMaxretries() {
		return maxretries;
	}


	public void setMaxretries(Long maxretries) {
		this.maxretries = maxretries;
	}


	public Date getEarliestRetryTime() {
		return earliestRetryTime;
	}


	public void setEarliestRetryTime(Date earliestRetryTime) {
		this.earliestRetryTime = earliestRetryTime;
	}


	@Override
	public String toString() {
		return "\n\nPaymentNotification [\n\t\tamount=" + amount + ", \n\t\tcurrencyCode=" + currencyCode
				+ ", \n\t\tinvoiceNo=" + invoiceNo + ", \n\t\tpaymentMode=" + paymentMode + ", \n\t\ttransactionDate="
				+ transactionDate + ", \n\t\trecTimeStamp=" + recTimeStamp + ", \n\t\ttransactionRef=" + transactionRef
				+ ", \n\t\tsystemID=" + systemID + ", \n\t\tstatus=" + status + ", \n\t\tretrycount=" + retrycount
				+ ", \n\t\tmaxretries=" + maxretries + ", \n\t\tearliestRetryTime=" + earliestRetryTime + "\n]\n\n";
	}
	
	
	
	

}

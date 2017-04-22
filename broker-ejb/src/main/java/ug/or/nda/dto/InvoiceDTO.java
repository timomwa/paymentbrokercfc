package ug.or.nda.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlType;

import ug.or.nda.wsi.InvoiceStatus;


@XmlType(name="invoice", namespace="http://service.nda.or.ug")
public class InvoiceDTO implements Serializable {

	private static final long serialVersionUID = -6044267411024241400L;
	private String invoiceNo;
	private String description;
	private BigDecimal amount;
	private String currencyCode;
	private String customerName;
	private Date dueDate;
	private String reference1;
	private InvoiceStatus status;
	private Boolean paymentExists;
	
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getAmount() {
		return amount!=null ? amount: BigDecimal.ZERO;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getReference1() {
		return reference1;
	}
	public void setReference1(String reference1) {
		this.reference1 = reference1;
	}
	public InvoiceStatus getStatus() {
		return status;
	}
	public void setStatus(InvoiceStatus status) {
		this.status = status;
	}
	public Boolean getPaymentExists() {
		return paymentExists;
	}
	public void setPaymentExists(Boolean paymentExists) {
		this.paymentExists = paymentExists;
	}
	
	@Override
	public String toString() {
		return "\n\nInvoiceDTO [\n\t\tinvoiceNo=" + invoiceNo + ", \n\t\tdescription=" + description + ", \n\t\tamount="
				+ amount + ", \n\t\tcurrencyCode=" + currencyCode + ", \n\t\tcustomerName=" + customerName
				+ ", \n\t\tdueDate=" + dueDate + ", \n\t\treference1=" + reference1 + ", \n\t\tstatus=" + status
				+ ", \n\t\tpaymentExists=" + paymentExists + "\n]\n\n";
	}
	
}

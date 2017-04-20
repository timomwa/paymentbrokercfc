package ug.or.nda.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="invoice")
public class Invoice extends AbstractEntity{

	private static final long serialVersionUID = -3597405125696176336L;
	
	@Column(name="amount", scale=5, precision=10)
	private BigDecimal amount;
	
	@Column(name="currencyCode")
	private String currencyCode;
	
	@Column(name="customerName")
	private String customerName;
	
	@Column(name="description", length=1024)
	private String description;
	
	@Column(name="dueDate")
	private Date dueDate;
	
	@Column(name="invoiceNo", length=300)
	private String invoiceNo;
	
	@Column(name="reference1", length=300)
	private String reference1;
	
	public BigDecimal getAmount() {
		return amount;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getReference1() {
		return reference1;
	}
	public void setReference1(String reference1) {
		this.reference1 = reference1;
	}
	
	@Override
	public String toString() {
		return "\n\nInvoice [\n\t\tamount=" + amount + ", \n\t\tcurrencyCode=" + currencyCode + ", \n\t\tcustomerName="
				+ customerName + ", \n\t\tdescription=" + description + ", \n\t\tdueDate=" + dueDate
				+ ", \n\t\tinvoiceNo=" + invoiceNo + ", \n\t\treference1=" + reference1 + "\n]\n\n";
	}
	
	

}

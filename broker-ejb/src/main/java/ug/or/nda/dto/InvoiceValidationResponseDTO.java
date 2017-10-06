package ug.or.nda.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="invoiceValidationResponse", namespace="http://service.nda.or.ug")
public class InvoiceValidationResponseDTO implements Serializable {

	private static final long serialVersionUID = -8582351670605528433L;
	
	private InvoiceDTO invoice;
	private Integer statusCode;
	private String statusMessage;
	
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public InvoiceDTO getInvoice() {
		return invoice;
	}
	public void setInvoice(InvoiceDTO invoice) {
		this.invoice = invoice;
	}
	@Override
	public String toString() {
		return "\n\nInvoiceValidationResponse [\n\t\tinvoice=" + invoice + ", \n\t\tstatusCode=" + statusCode
				+ ", \n\t\tstatusMessage=" + statusMessage + "\n]\n\n";
	}
	
	

}

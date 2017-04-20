package ug.or.nda.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="invoiceValidationRequest", namespace="http://service.nda.or.ug")
public class InvoiceValidationRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3635480331572742573L;
	private RequestHeaderDTO requestHeader;
	private String invoiceNo;
	
	public RequestHeaderDTO getRequestHeader() {
		return requestHeader;
	}
	public void setRequestHeader(RequestHeaderDTO requestHeader) {
		this.requestHeader = requestHeader;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	@Override
	public String toString() {
		return "\n\nInvoiceValidationRequest [\n\t\trequestHeader=" + requestHeader + ", \n\t\tinvoiceNo=" + invoiceNo
				+ "\n]\n\n";
	}
	
	
	
}

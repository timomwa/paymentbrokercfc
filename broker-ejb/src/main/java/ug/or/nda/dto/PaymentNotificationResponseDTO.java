package ug.or.nda.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="paymentNotificationResponse", namespace="http://service.nda.or.ug")
public class PaymentNotificationResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5474983200726913213L;
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
	@Override
	public String toString() {
		return "\n\nPaymentNotificationResponse [\n\t\tstatusCode=" + statusCode + ", \n\t\tstatusMessage="
				+ statusMessage + "\n]\n\n";
	}
	
	

}

package ug.or.nda.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="paymentNotificationRequest", namespace="http://service.nda.or.ug")
public class PaymentNotificationRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5124786601127666890L;
	private RequestHeaderDTO requestHeader;
	private PaymentNotificationDTO paymentNotification;
	
	public RequestHeaderDTO getRequestHeader() {
		return requestHeader;
	}
	public void setRequestHeader(RequestHeaderDTO requestHeader) {
		this.requestHeader = requestHeader;
	}
	public PaymentNotificationDTO getPaymentNotification() {
		return paymentNotification;
	}
	public void setPaymentNotification(PaymentNotificationDTO paymentNotification) {
		this.paymentNotification = paymentNotification;
	}
	
	@Override
	public String toString() {
		return "\n\nPaymentNotificationRequest [\n\t\trequestHeader=" + requestHeader + ", \n\t\tpaymentNotification="
				+ paymentNotification + "\n]\n\n";
	}
	
	

}

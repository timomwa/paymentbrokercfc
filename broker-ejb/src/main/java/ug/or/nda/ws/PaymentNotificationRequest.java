
package ug.or.nda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paymentNotificationRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paymentNotificationRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paymentNotification" type="{http://ws.nda.or.ug}paymentNotification" minOccurs="0"/>
 *         &lt;element name="requestHeader" type="{http://ws.nda.or.ug}requestHeader" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paymentNotificationRequest", propOrder = {
    "paymentNotification",
    "requestHeader"
})
public class PaymentNotificationRequest {

    protected PaymentNotification paymentNotification;
    protected RequestHeader requestHeader;

    /**
     * Gets the value of the paymentNotification property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentNotification }
     *     
     */
    public PaymentNotification getPaymentNotification() {
        return paymentNotification;
    }

    /**
     * Sets the value of the paymentNotification property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentNotification }
     *     
     */
    public void setPaymentNotification(PaymentNotification value) {
        this.paymentNotification = value;
    }

    /**
     * Gets the value of the requestHeader property.
     * 
     * @return
     *     possible object is
     *     {@link RequestHeader }
     *     
     */
    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    /**
     * Sets the value of the requestHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestHeader }
     *     
     */
    public void setRequestHeader(RequestHeader value) {
        this.requestHeader = value;
    }

}

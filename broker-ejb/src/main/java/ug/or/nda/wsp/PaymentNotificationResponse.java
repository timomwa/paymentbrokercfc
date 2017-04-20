
package ug.or.nda.wsp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paymentNotificationResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paymentNotificationResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://ws.nda.or.ug}paymentNotificationResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paymentNotificationResponse", propOrder = {
    "_return"
})
public class PaymentNotificationResponse {

    @XmlElement(name = "return")
    protected ug.or.nda.ws.PaymentNotificationResponse _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link ug.or.nda.ws.PaymentNotificationResponse }
     *     
     */
    public ug.or.nda.ws.PaymentNotificationResponse getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link ug.or.nda.ws.PaymentNotificationResponse }
     *     
     */
    public void setReturn(ug.or.nda.ws.PaymentNotificationResponse value) {
        this._return = value;
    }

}

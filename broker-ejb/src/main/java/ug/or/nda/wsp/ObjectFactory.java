
package ug.or.nda.wsp;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ug.or.nda.wsp package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PaymentNotificationResponse_QNAME = new QName("http://wsp.nda.or.ug", "paymentNotificationResponse");
    private final static QName _PaymentNotification_QNAME = new QName("http://wsp.nda.or.ug", "paymentNotification");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ug.or.nda.wsp
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PaymentNotificationResponse }
     * 
     */
    public PaymentNotificationResponse createPaymentNotificationResponse() {
        return new PaymentNotificationResponse();
    }

    /**
     * Create an instance of {@link PaymentNotification }
     * 
     */
    public PaymentNotification createPaymentNotification() {
        return new PaymentNotification();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaymentNotificationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsp.nda.or.ug", name = "paymentNotificationResponse")
    public JAXBElement<PaymentNotificationResponse> createPaymentNotificationResponse(PaymentNotificationResponse value) {
        return new JAXBElement<PaymentNotificationResponse>(_PaymentNotificationResponse_QNAME, PaymentNotificationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaymentNotification }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsp.nda.or.ug", name = "paymentNotification")
    public JAXBElement<PaymentNotification> createPaymentNotification(PaymentNotification value) {
        return new JAXBElement<PaymentNotification>(_PaymentNotification_QNAME, PaymentNotification.class, null, value);
    }

}

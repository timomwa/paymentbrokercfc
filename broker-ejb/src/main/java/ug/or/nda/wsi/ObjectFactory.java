
package ug.or.nda.wsi;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ug.or.nda.wsi package. 
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

    private final static QName _ValidateInvoice_QNAME = new QName("http://wsi.nda.or.ug", "validateInvoice");
    private final static QName _ValidateInvoiceResponse_QNAME = new QName("http://wsi.nda.or.ug", "validateInvoiceResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ug.or.nda.wsi
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ValidateInvoiceResponse }
     * 
     */
    public ValidateInvoiceResponse createValidateInvoiceResponse() {
        return new ValidateInvoiceResponse();
    }

    /**
     * Create an instance of {@link ValidateInvoice }
     * 
     */
    public ValidateInvoice createValidateInvoice() {
        return new ValidateInvoice();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateInvoice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsi.nda.or.ug", name = "validateInvoice")
    public JAXBElement<ValidateInvoice> createValidateInvoice(ValidateInvoice value) {
        return new JAXBElement<ValidateInvoice>(_ValidateInvoice_QNAME, ValidateInvoice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateInvoiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsi.nda.or.ug", name = "validateInvoiceResponse")
    public JAXBElement<ValidateInvoiceResponse> createValidateInvoiceResponse(ValidateInvoiceResponse value) {
        return new JAXBElement<ValidateInvoiceResponse>(_ValidateInvoiceResponse_QNAME, ValidateInvoiceResponse.class, null, value);
    }

}

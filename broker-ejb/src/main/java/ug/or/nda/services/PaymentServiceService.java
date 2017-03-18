
package ug.or.nda.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.3-b02-
 * Generated source version: 2.0
 * 
 */
@WebServiceClient(name = "PaymentServiceService", targetNamespace = "http://services.nda.or.ug", wsdlLocation = "http://localhost:8080/broker/invoice/payment/v1.0?wsdl")
public class PaymentServiceService
    extends Service
{

    private final static URL PAYMENTSERVICESERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(ug.or.nda.services.PaymentServiceService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = ug.or.nda.services.PaymentServiceService.class.getResource(".");
            url = new URL(baseUrl, "http://localhost:8080/broker/invoice/payment/v1.0?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://localhost:8080/broker/invoice/payment/v1.0?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        PAYMENTSERVICESERVICE_WSDL_LOCATION = url;
    }

    public PaymentServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PaymentServiceService() {
        super(PAYMENTSERVICESERVICE_WSDL_LOCATION, new QName("http://services.nda.or.ug", "PaymentServiceService"));
    }

    /**
     * 
     * @return
     *     returns PaymentService
     */
    @WebEndpoint(name = "paymentServicePort")
    public PaymentService getPaymentServicePort() {
        return super.getPort(new QName("http://services.nda.or.ug", "paymentServicePort"), PaymentService.class);
    }

}

package ug.or.nda.wsi;

public class InvoiceWebServiceProxy implements ug.or.nda.wsi.InvoiceWebService_PortType {
  private String _endpoint = null;
  private ug.or.nda.wsi.InvoiceWebService_PortType invoiceWebService_PortType = null;
  
  public InvoiceWebServiceProxy() {
    _initInvoiceWebServiceProxy();
  }
  
  public InvoiceWebServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initInvoiceWebServiceProxy();
  }
  
  private void _initInvoiceWebServiceProxy() {
    try {
      invoiceWebService_PortType = (new ug.or.nda.wsi.InvoiceWebService_ServiceLocator()).getinvoiceWebServicePort();
      if (invoiceWebService_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)invoiceWebService_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)invoiceWebService_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (invoiceWebService_PortType != null)
      ((javax.xml.rpc.Stub)invoiceWebService_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ug.or.nda.wsi.InvoiceWebService_PortType getInvoiceWebService_PortType() {
    if (invoiceWebService_PortType == null)
      _initInvoiceWebServiceProxy();
    return invoiceWebService_PortType;
  }
  
  public ug.or.nda.ws.InvoiceValidationResponse validateInvoice(ug.or.nda.ws.InvoiceValidationRequest arg0) throws java.rmi.RemoteException{
    if (invoiceWebService_PortType == null)
      _initInvoiceWebServiceProxy();
    return invoiceWebService_PortType.validateInvoice(arg0);
  }
  
  
}
/**
 * InvoiceWebService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ug.or.nda.wsi;

public class InvoiceWebService_ServiceLocator extends org.apache.axis.client.Service{// implements ug.or.nda.wsi.InvoiceWebService_Service {

    public InvoiceWebService_ServiceLocator() {
    }


    public InvoiceWebService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public InvoiceWebService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for invoiceWebServicePort
    private java.lang.String invoiceWebServicePort_address = "http://localhost:8080/ndamisws/payments/service/v1.0";

    public java.lang.String getinvoiceWebServicePortAddress() {
        return invoiceWebServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String invoiceWebServicePortWSDDServiceName = "invoiceWebServicePort";

    public java.lang.String getinvoiceWebServicePortWSDDServiceName() {
        return invoiceWebServicePortWSDDServiceName;
    }

    public void setinvoiceWebServicePortWSDDServiceName(java.lang.String name) {
        invoiceWebServicePortWSDDServiceName = name;
    }

    public ug.or.nda.wsi.InvoiceWebService_PortType getinvoiceWebServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(invoiceWebServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getinvoiceWebServicePort(endpoint);
    }

    public ug.or.nda.wsi.InvoiceWebService_PortType getinvoiceWebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            ug.or.nda.wsi.InvoiceWebServiceBindingStub _stub = new ug.or.nda.wsi.InvoiceWebServiceBindingStub(portAddress, this);
            _stub.setPortName(getinvoiceWebServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setinvoiceWebServicePortEndpointAddress(java.lang.String address) {
        invoiceWebServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (ug.or.nda.wsi.InvoiceWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                ug.or.nda.wsi.InvoiceWebServiceBindingStub _stub = new ug.or.nda.wsi.InvoiceWebServiceBindingStub(new java.net.URL(invoiceWebServicePort_address), this);
                _stub.setPortName(getinvoiceWebServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("invoiceWebServicePort".equals(inputPortName)) {
            return getinvoiceWebServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://wsi.nda.or.ug", "invoiceWebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://wsi.nda.or.ug", "invoiceWebServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("invoiceWebServicePort".equals(portName)) {
            setinvoiceWebServicePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}

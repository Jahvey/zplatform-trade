/**
 * ColltnAndPmtServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.zlebank.zplatform.trade.bosspay.client;

public class ColltnAndPmtServiceLocator extends org.apache.axis.client.Service implements com.zlebank.zplatform.trade.bosspay.client.ColltnAndPmtService {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4990705024754688032L;

	public ColltnAndPmtServiceLocator() {
    }


    public ColltnAndPmtServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ColltnAndPmtServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ColltnAndPmtServiceImplPort
    private java.lang.String ColltnAndPmtServiceImplPort_address = "http://118.212.132.99:88/BossPayS/EnterpriseService/colltnAndPmt";

    public java.lang.String getColltnAndPmtServiceImplPortAddress() {
        return ColltnAndPmtServiceImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ColltnAndPmtServiceImplPortWSDDServiceName = "ColltnAndPmtServiceImplPort";

    public java.lang.String getColltnAndPmtServiceImplPortWSDDServiceName() {
        return ColltnAndPmtServiceImplPortWSDDServiceName;
    }

    public void setColltnAndPmtServiceImplPortWSDDServiceName(java.lang.String name) {
        ColltnAndPmtServiceImplPortWSDDServiceName = name;
    }

    public com.zlebank.zplatform.trade.bosspay.service.ColltnAndPmtService getColltnAndPmtServiceImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ColltnAndPmtServiceImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getColltnAndPmtServiceImplPort(endpoint);
    }

    public com.zlebank.zplatform.trade.bosspay.service.ColltnAndPmtService getColltnAndPmtServiceImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	com.zlebank.zplatform.trade.bosspay.client.ColltnAndPmtServiceSoapBindingStub _stub = new com.zlebank.zplatform.trade.bosspay.client.ColltnAndPmtServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getColltnAndPmtServiceImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setColltnAndPmtServiceImplPortEndpointAddress(java.lang.String address) {
        ColltnAndPmtServiceImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @SuppressWarnings("rawtypes")
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.zlebank.zplatform.trade.bosspay.service.ColltnAndPmtService.class.isAssignableFrom(serviceEndpointInterface)) {
            	com.zlebank.zplatform.trade.bosspay.client.ColltnAndPmtServiceSoapBindingStub _stub = new com.zlebank.zplatform.trade.bosspay.client.ColltnAndPmtServiceSoapBindingStub(new java.net.URL(ColltnAndPmtServiceImplPort_address), this);
                _stub.setPortName(getColltnAndPmtServiceImplPortWSDDServiceName());
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
    @SuppressWarnings("rawtypes")
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ColltnAndPmtServiceImplPort".equals(inputPortName)) {
            return getColltnAndPmtServiceImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://impl.service.wisvalley.bosspay.com/", "ColltnAndPmtService");
    }
    @SuppressWarnings("rawtypes")
    private java.util.HashSet ports = null;
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://impl.service.wisvalley.bosspay.com/", "ColltnAndPmtServiceImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ColltnAndPmtServiceImplPort".equals(portName)) {
            setColltnAndPmtServiceImplPortEndpointAddress(address);
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

URL wsdlLocation = null;
		String endpoint = "http://localhost:8080/broker/invoice/payment/v1.0";
		try {
			wsdlLocation = new URL(endpoint+"?wsdl");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
	
		QName serviceName = new QName("http://services.nda.or.ug","PaymentServiceService");

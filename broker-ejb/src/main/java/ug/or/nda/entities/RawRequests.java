package ug.or.nda.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ug.or.nda.constant.RequestStatus;
import ug.or.nda.constant.RequestType;

@Entity
@Table(name="raw_requests")
public class RawRequests extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6739663218440120579L;
	
	@Column(name="requestType")
	@Enumerated(EnumType.STRING)
	private RequestType requestType;
	
	@Column(name="payload", length=4060 )
	private String payload;
	
	@Column(name="timeStamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeStamp;
	
	@Column(name="requstStatus")
	@Enumerated(EnumType.STRING)
	private RequestStatus requstStatus;

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public RequestStatus getRequstStatus() {
		return requstStatus;
	}

	public void setRequstStatus(RequestStatus requstStatus) {
		this.requstStatus = requstStatus;
	}

	@Override
	public String toString() {
		return "\n\nRawRequests [\n\t\trequestType=" + requestType + ", \n\t\tpayload=" + payload + ", \n\t\ttimeStamp="
				+ timeStamp + ", \n\t\trequstStatus=" + requstStatus + "\n]\n\n";
	}
	
	

}

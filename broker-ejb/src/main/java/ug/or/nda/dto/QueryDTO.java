package ug.or.nda.dto;

import java.io.Serializable;
import java.util.Date;

public class QueryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 11204838202874L;
	private RequestHeaderDTO requestHeader;
	private String query;
	private Integer start;
	private Integer limit;
	private Date dateFrom;
	private Date dateTo;
	
	public RequestHeaderDTO getRequestHeader() {
		return requestHeader;
	}
	public void setRequestHeader(RequestHeaderDTO requestHeader) {
		this.requestHeader = requestHeader;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	@Override
	public String toString() {
		return "\n\nQueryDTO [\n\t\trequestHeader=" + requestHeader + ", \n\t\tquery=" + query + ", \n\t\tstart="
				+ start + ", \n\t\tlimit=" + limit + ", \n\t\tdateFrom=" + dateFrom + ", \n\t\tdateTo=" + dateTo
				+ "\n]\n\n";
	}
	
	

}

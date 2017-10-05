package ug.or.nda.dto;

import java.io.Serializable;

public class ConfigurationRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8987515513428702957L;
	
	private Action action;
	private String configKey;
	private String configValue;
	private RequestHeaderDTO requestHeader;
	
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public String getConfigKey() {
		return configKey;
	}
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	
	public RequestHeaderDTO getRequestHeader() {
		return requestHeader;
	}
	public void setRequestHeader(RequestHeaderDTO requestHeader) {
		this.requestHeader = requestHeader;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConfigurationRequest [\n\taction=");
		builder.append(action);
		builder.append(",\n\tconfigKey=");
		builder.append(configKey);
		builder.append(",\n\tconfigValue=");
		builder.append(configValue);
		builder.append(",\n\trequestHeader=");
		builder.append(requestHeader);
		builder.append("\n]");
		return builder.toString();
	}
	
	

}

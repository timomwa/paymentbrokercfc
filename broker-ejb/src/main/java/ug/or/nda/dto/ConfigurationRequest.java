package ug.or.nda.dto;

import java.io.Serializable;

public class ConfigurationRequest implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6106260524994863568L;
	private Action action;
	private String configKey;
	private String configValue;
	
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConfigurationRequest [\n\taction=");
		builder.append(action);
		builder.append(",\n\tconfigKey=");
		builder.append(configKey);
		builder.append(",\n\tconfigValue=");
		builder.append(configValue);
		builder.append("\n]");
		return builder.toString();
	}
	
	

}

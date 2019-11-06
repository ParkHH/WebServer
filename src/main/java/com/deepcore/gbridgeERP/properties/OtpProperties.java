package com.deepcore.gbridgeERP.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="otp")
public class OtpProperties {
	private String serialNumber = "";			//OTP 계정 SerialNo
	private String user_name = "";				//OTP 계정 이름
	private String hostName = "";				//host 이름 
	
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
}

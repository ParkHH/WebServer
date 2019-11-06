package com.deepcore.gbridgeERP.model.service;


/**
 * 
 * @author ParkHyeonho
 * OTP 적용을 위한 Key 생성 및 요청 URL 생성 및 입력된 OTP 값 유효성 Check 하는 기능을 수행하는 Service
 */
public interface OtpService {
	public String createSerialKeyAndUrl(boolean toggle);
	public boolean check_code(String user_code, String encodedKey);
}

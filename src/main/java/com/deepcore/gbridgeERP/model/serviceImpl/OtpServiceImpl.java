package com.deepcore.gbridgeERP.model.serviceImpl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.deepcore.gbridgeERP.model.service.OtpService;
import com.deepcore.gbridgeERP.properties.OtpProperties;

/**
 * 
 * @author ParkHyeonho
 * OTP 관련 serialKey 및 요청 URL 생성 및 입력한 OTP 값 유효성 Check 기능을 수행하는 Service Class
 */
@Service
public class OtpServiceImpl implements OtpService {

	@Autowired
	OtpProperties otpProperties;
	
	
	//----------------------------------------------------------------------------------------
	// 1. 계정 연결 SerialKey 및 요청 URL 생성
	//----------------------------------------------------------------------------------------
	@Override
	public String createSerialKeyAndUrl(boolean toggle) {
		String encodedKey = otpProperties.getSerialNumber();
		String userName = otpProperties.getUser_name();
		String hostName = otpProperties.getHostName();
		
		//*********************************************************
		// toggle 값 (true : SeriallKey 생성) 
		//*********************************************************
		if(toggle) {
			// Allocating the buffer
	//      byte[] buffer = new byte[secretSize + numOfScratchCodes * scratchCodeSize];
	        byte[] buffer = new byte[5 + 5 * 5];
	         
	        // Filling the buffer with random numbers.
	        // Notice: you want to reuse the same random generator
	        // while generating larger random number sequences.
	        new Random().nextBytes(buffer);
	 
	        // Getting the key and converting it to Base32
	        Base32 codec = new Base32();
	//      byte[] secretKey = Arrays.copyOf(buffer, secretSize);
	        byte[] secretKey = Arrays.copyOf(buffer, 10);
	        byte[] bEncodedKey = codec.encode(secretKey);
	         
	        // 생성된 Key!
	        encodedKey = new String(bEncodedKey);
	        otpProperties.setSerialNumber(encodedKey);		// Properties 속성값으로 넣어줌	  
	        System.out.println("encodedKey : " + encodedKey);  
		}
//      String url = getQRBarcodeURL(userName, hostName, secretKeyStr);
        // userName과 hostName은 변수로 받아서 넣어야 하지만, 여기선 테스트를 위해 하드코딩 해줬다.
        String url = getQRBarcodeURL(userName, hostName, encodedKey); // 생성된 바코드 주소!
        System.out.println("URL : " + url);
        
        /*
        String view = "/otpPage";
         
        req.setAttribute("encodedKey", encodedKey);
        req.setAttribute("url", url);
         
        req.getRequestDispatcher(view).forward(req, res);
        */
        
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"encodedKey\":\""+encodedKey+"\",");
        sb.append("\"url\":\""+url+"\"");
        sb.append("}");        
		
		return sb.toString();
	}
	
	//----------------------------------------------------------------------------------------
	// 1-1. URL 생성
	//----------------------------------------------------------------------------------------
	public static String getQRBarcodeURL(String user, String host, String secret) {
        String format = "http://chart.apis.google.com/chart?cht=qr&amp;chs=300x300&amp;chl=otpauth://totp/%s@%s%%3Fsecret%%3D%s&amp;chld=H|0";
         
        return String.format(format, user, host, secret);
    }
	
	
	
	
	
	
	
	//----------------------------------------------------------------------------------------
	// 2. 입력받은 OtpNumber 유효성 Check
	//----------------------------------------------------------------------------------------
	@Override
	public boolean check_code(String in_user_code, String in_encodedKey) {
//		String user_codeStr = req.getParameter("user_code");
//      long user_code = Integer.parseInt(user_codeStr);
//      String encodedKey = req.getParameter("encodedKey");
		
		long user_code = Integer.parseInt(in_user_code);
		String encodedKey = in_encodedKey;
        long l = new Date().getTime();
        long ll =  l / 30000;
        
        //System.out.println("OtpResultServlet > service > parameters ( encodedKey : "+encodedKey+", user_code : "+in_user_code+" )");
        
        boolean check_code = false;
        try {
            // 키, 코드, 시간으로 일회용 비밀번호가 맞는지 일치 여부 확인.
            check_code = check_code(encodedKey, user_code, ll);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
         
        // 일치한다면 true.
        //System.out.println("check_code : " + check_code);
        return check_code;
	}
	
	//----------------------------------------------------------------------------------------
	// 2-1. 유효성 Check
	//----------------------------------------------------------------------------------------
	private static boolean check_code(String secret, long code, long t) throws NoSuchAlgorithmException, InvalidKeyException {
        Base32 codec = new Base32();
        byte[] decodedKey = codec.decode(secret);
 
        // Window is used to check codes generated in the near past.
        // You can use this value to tune how far you're willing to go.
        int window = 0;
        for (int i = -window; i <= window; ++i) {
            long hash = verify_code(decodedKey, t + i);
 
            if (hash == code) {
                return true;
            }
        }
 
        // The validation code is invalid.
        return false;
    }
	     
	 
    private static int verify_code(byte[] key, long t)
            throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = new byte[8];
        long value = t;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
 
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);
 
        int offset = hash[20 - 1] & 0xF;
 
        // We're using a long because Java hasn't got unsigned int.
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            // We are dealing with signed bytes:
            // we just keep the first byte.
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
 
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;
 
        return (int) truncatedHash;
    }

}

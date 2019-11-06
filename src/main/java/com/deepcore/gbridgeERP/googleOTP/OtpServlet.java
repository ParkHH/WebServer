package com.deepcore.gbridgeERP.googleOTP;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base32;
import org.springframework.web.bind.annotation.ResponseBody;

public class OtpServlet extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
        String encodedKey = new String(bEncodedKey);
         
        System.out.println("encodedKey : " + encodedKey);
         
//      String url = getQRBarcodeURL(userName, hostName, secretKeyStr);
        // userName과 hostName은 변수로 받아서 넣어야 하지만, 여기선 테스트를 위해 하드코딩 해줬다.
        String url = getQRBarcodeURL("parkhyeonho92", "gmail.com", encodedKey); // 생성된 바코드 주소!
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
        String otpInfo = sb.toString();
        
        req.setAttribute("otpInfo", otpInfo);
        req.getRequestDispatcher("/otp/otp.html").forward(req, res);
	}

	
	
	
	 public static String getQRBarcodeURL(String user, String host, String secret) {
	        String format = "http://chart.apis.google.com/chart?cht=qr&amp;chs=300x300&amp;chl=otpauth://totp/%s@%s%%3Fsecret%%3D%s&amp;chld=H|0";
	         
	        return String.format(format, user, host, secret);
	    }
}

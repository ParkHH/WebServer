package com.deepcore.gbridgeERP.common;


import java.security.MessageDigest;

/**
 * 
 * @author ParkHyeonho
 * 로그인을 위해 넘겨받은 Parameter 중 비밀번호를 암호화 하는 Class
 */
public class Sha256 {
	
	//--------------------------------------------------------------
	// 비밀번호 암호화 적용!!
	//--------------------------------------------------------------
	public static String applySha256(String password) {
		StringBuilder sb = new StringBuilder();
		try {			
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte[] dataArr = md.digest();
			
			
			for(int i=0; i<dataArr.length; i++) {		
				// 1. 기존 사용 수식
				//sb.append(Integer.toString((dataArr[i]&0xff)+0x100,16).substring(1));
				// 2. C# 에서의 암호화 결과와 동일하게 하기 위해 새로 작성한 수식
				sb.append(Integer.toHexString(0xff & dataArr[i]));
			}
						
			System.out.println("암호화 완료 비밀번호 길이 : "+sb.toString().length());
			System.out.println("암호화 완료 비밀번호 : "+sb.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return sb.toString();
	}
	
	
//	public static void main(String[] args) {
//		System.out.println("*"+Sha256.applySha256("zxc123").toUpperCase());
//	}
}

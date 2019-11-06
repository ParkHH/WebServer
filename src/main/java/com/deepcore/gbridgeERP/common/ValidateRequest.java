package com.deepcore.gbridgeERP.common;

public class ValidateRequest {
	
	// FileUpload 요청정보 유효성 검증
	public static boolean validateRequestParameter(String file, String company, String product) {
		String[] fileParameters = {"C", "P", "S", "V"};
		String[] companyParameters = {"0001", "0002", "0003", "0005", "0006", "0007", "0009"};
		String[] productParameters = {"1", "2", "3", "4", "5", "7", "9"};
		boolean result = false;
		
		// FileParameter 유효성 검증
		for(int i=0; i<fileParameters.length; i++) {
			String parameter = fileParameters[i];
			if(parameter.equals(file)) {
				System.out.println("FileParameter : 유효한 Parameter값 입니다.");
				result = true;
				break;
			}
		}
		
		// CompanyParameter 유효성 검증
		for(int i=0; i<companyParameters.length; i++) {
			String parameter = companyParameters[i];
			if(parameter.equals(company)) {
				System.out.println("CompanyParameter : 유효한 Parameter값 입니다. ");
				result = true;
				break;
			}
		}
		
		// ProductParameter 유효성 검증
		for(int i=0; i<productParameters.length; i++) {
			String parameter = productParameters[i];
			if(parameter.equals(product)) {
				System.out.println("productParameter : 유효한 Parameter값 입니다. ");
				result = true;
				break;
			}
		}
		
		return result;
	}
}

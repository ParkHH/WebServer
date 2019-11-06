package com.deepcore.gbridgeERP.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * 
 * @author ParkHyeonho
 * 로그인 하지 않고 Service 를 이용하려 할경우 차단하기 위해 사용되는 AOP Class
 * >>> 문제점
 * 		session check 를 위해 aop 동작시 Method 반환형에 대한 Casting ERROR 가 계속 발생함 (적용취소 190902)
 */

//@Aspect
//@Component		// bean 으로 등록!
public class SessionCheckAspect {
	
	String[] expectArr = {
			"/login",
			"/",
			"/otp/request",
			"/request"
			};
	
	@Pointcut("execution(* com.deepcore.gbridgeERP.controller..*(..))")
	public void sessionCheck() {	}
	
	
	@Around("sessionCheck()")
	public String loginCheck(ProceedingJoinPoint joinPoint) throws Throwable{
		System.out.println("SessionCheckAspect > loginCheck 동작!");
		HttpServletRequest request = null;
		String viewName = null;
		String requestURL = null;
		int count = 0;
		
		Object[] objArr = joinPoint.getArgs();
		
		for(int i=0; i<objArr.length; i++) {
			Object obj = objArr[i];
			System.out.println(obj.getClass());
			if(obj instanceof HttpServletRequest) {
				request = (HttpServletRequest)obj;				
				requestURL = request.getRequestURI().toString();
				System.out.println("들어온 요청 URL : "+requestURL);
				
				for(int j=0; j<expectArr.length; j++) {
					System.out.println(expectArr[j]);
					if(requestURL.equals(expectArr[j])) {	
						System.out.println("count 증가!");
						count++;
					}
				}
			}
		}
		
		if(request != null && count<1) {
			if(request.getSession().getAttribute("ACNT_ID")==null || request.getSession().getAttribute("codeCheckResult").equals("false")) {
				viewName = "/";
			}else {
				viewName = joinPoint.proceed().toString();
				String methodName = joinPoint.getSignature().getName();
				System.out.println("SessionCheckAspect > loginCheck > method 반환값 : "+viewName);
				System.out.println("SessionCheckAspect > 피호출 Method : "+methodName);
			}
		}else {
			viewName = joinPoint.proceed().toString();
			String methodName = joinPoint.getSignature().getName();
			System.out.println("SessionCheckAspect > loginCheck > method 반환값 : "+viewName);
			System.out.println("SessionCheckAspect > 피호출 Method : "+methodName);
		}
		
		return viewName;
	}
	
}	

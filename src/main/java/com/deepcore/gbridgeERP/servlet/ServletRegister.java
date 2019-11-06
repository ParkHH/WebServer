package com.deepcore.gbridgeERP.servlet;


import javax.servlet.http.HttpServlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deepcore.gbridgeERP.googleOTP.OtpResultServlet;
import com.deepcore.gbridgeERP.googleOTP.OtpServlet;

//@Configuration
public class ServletRegister {
	/*
	@Bean
	public ServletRegistrationBean otpServlet() {
		ServletRegistrationBean bean = new ServletRegistrationBean(new OtpServlet());
		bean.addUrlMappings("/otp/request");
		
		return bean;
	}
	
	@Bean
	public ServletRegistrationBean otpResultServlet() {
		ServletRegistrationBean bean = new ServletRegistrationBean(new OtpResultServlet());
		bean.addUrlMappings("/otp/result");
		
		return bean;
	}
	*/
}

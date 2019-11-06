package com.deepcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

import com.deepcore.gbridgeERP.properties.OtpProperties;

@SpringBootApplication
//@EnableAspectJAutoProxy
@EnableConfigurationProperties(OtpProperties.class)
public class GbridgeFileAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(GbridgeFileAdminApplication.class, args);
	}

}

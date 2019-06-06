package com.jboscloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class JboscloudServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JboscloudServerApplication.class, args);
	}

}

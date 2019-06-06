package com.jboscloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class JbosCloudSupportApplication {

	public static void main(String[] args) {
		SpringApplication.run(JbosCloudSupportApplication.class, args);
	}

}

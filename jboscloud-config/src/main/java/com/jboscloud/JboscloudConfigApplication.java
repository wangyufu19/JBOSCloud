package com.jboscloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class JboscloudConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(JboscloudConfigApplication.class, args);
	}

}

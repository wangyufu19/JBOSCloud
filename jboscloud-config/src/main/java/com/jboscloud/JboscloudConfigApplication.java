package com.jboscloud;

import com.jboscloud.kafka.KafkaSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigServer
public class JboscloudConfigApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context=SpringApplication.run(JboscloudConfigApplication.class, args);
//		KafkaSender sender = context.getBean(KafkaSender.class);
//		sender.send();
	}

}

package com.jboscloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableEurekaClient
@EnableZuulProxy
public class JbosCloudOpenApiApplication {


	public static void main(String[] args) {
		SpringApplication.run(JbosCloudOpenApiApplication.class, args);
	}

}

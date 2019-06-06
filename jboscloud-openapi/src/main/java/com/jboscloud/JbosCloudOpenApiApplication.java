package com.jboscloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import com.jboscloud.openapi.filter.GatewayFilter;

@SpringBootApplication
@EnableZuulProxy
public class JbosCloudOpenApiApplication {

	@Bean
	public GatewayFilter gatewayFilter(){
		return new GatewayFilter();
	}
	public static void main(String[] args) {
		SpringApplication.run(JbosCloudOpenApiApplication.class, args);
	}

}

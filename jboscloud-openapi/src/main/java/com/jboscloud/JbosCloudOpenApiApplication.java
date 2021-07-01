package com.jboscloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import com.jboscloud.openapi.filter.OpenApiFilter;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class JbosCloudOpenApiApplication {

	@Bean
	public OpenApiFilter getHttpAccessFilter(){
		return new OpenApiFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(JbosCloudOpenApiApplication.class, args);
	}

}

package com.jboscloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import com.jboscloud.openapi.filter.GatewayFilter;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
@EnableZuulProxy
public class JbosCloudOpenApiApplication {

	@Bean
	public GatewayFilter gatewayFilter(){
		return new GatewayFilter();
	}
	@Bean
	@RefreshScope
	@ConfigurationProperties("zuul")
	@Primary
	public ZuulProperties zuulProperties(){
		return new ZuulProperties();
	}
	public static void main(String[] args) {
		SpringApplication.run(JbosCloudOpenApiApplication.class, args);
	}

}

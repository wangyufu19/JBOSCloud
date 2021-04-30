package com.jboscloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import com.jboscloud.openapi.filter.HttpAccessFilter;

@SpringBootApplication
@EnableZuulProxy
public class JbosCloudOpenApiApplication {

	@Bean
	public HttpAccessFilter getHttpAccessFilter(){
		return new HttpAccessFilter();
	}
//	@Bean
//	@RefreshScope
//	@ConfigurationProperties("zuul")
//	@Primary
//	public ZuulProperties zuulProperties(){
//		return new ZuulProperties();
//	}
	public static void main(String[] args) {
		SpringApplication.run(JbosCloudOpenApiApplication.class, args);
	}

}

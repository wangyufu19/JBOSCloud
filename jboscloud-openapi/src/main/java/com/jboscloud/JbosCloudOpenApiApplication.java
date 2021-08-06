package com.jboscloud;
import com.jboscloud.common.config.AuthorizationServerConfig;
import com.jboscloud.common.config.ResourceServerConfig;
import com.jboscloud.common.spring.ApplicationContextListener;
import com.jboscloud.common.spring.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableEurekaClient
@EnableZuulProxy
@Slf4j
//@ComponentScan(excludeFilters = @ComponentScan.Filter(type =FilterType.ASSIGNABLE_TYPE, classes = {AuthorizationServerConfig.class,ResourceServerConfig.class}))
public class JbosCloudOpenApiApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(JbosCloudOpenApiApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication springApplication=new SpringApplication(JbosCloudOpenApiApplication.class);
		springApplication.addListeners(new ApplicationContextListener());
		ApplicationContext applicationContext=springApplication.run(args);
        log.info("******"+new BCryptPasswordEncoder().encode("123456"));
	}

}

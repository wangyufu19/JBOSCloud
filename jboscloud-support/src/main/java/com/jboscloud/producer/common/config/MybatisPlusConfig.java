package com.jboscloud.producer.common.config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.jboscloud.producer.mapper")
public class MybatisPlusConfig {


}
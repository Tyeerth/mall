package com.xdu.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2WebMvc
@MapperScan("com.xdu.sys.mapper")

public class ServiceSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceSysApplication.class, args);
    }
}

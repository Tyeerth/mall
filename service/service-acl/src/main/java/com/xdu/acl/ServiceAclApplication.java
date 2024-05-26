package com.xdu.acl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

//权限管理模块启动类
@SpringBootApplication
@MapperScan(basePackages = "com.xdu.acl.mapper")
@EnableSwagger2WebMvc
public class ServiceAclApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAclApplication.class, args);
    }

}
package com.hwua;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.hwua.mapper")
public class SsoUserModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoUserModuleApplication.class, args);
    }

}

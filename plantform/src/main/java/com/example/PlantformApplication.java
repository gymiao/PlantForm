package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
// 开启事务
public class PlantformApplication {
    
    public static void main(String[] args) {
        
        SpringApplication.run(PlantformApplication.class, args);
        log.info("服务器启动成功");
    }
    
}

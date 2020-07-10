package com.pragmaticbitbucket.app.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableDiscoveryClient
public class MobileAppWsApplication {

    @Autowired
    Environment environment;

    private String printPropertyValue;


    public static void main(String[] args) {
        SpringApplication.run(MobileAppWsApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public String printPropertyValue() {
        System.out.println("Property token.secret has value: " + environment.getProperty("token.secret"));
        return "Just testing";
    }
}

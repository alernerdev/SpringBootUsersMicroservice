package com.pragmaticbitbucket.app.ws;

import com.pragmaticbitbucket.app.ws.shared.FeignErrorDecoder;
import feign.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
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
        System.out.println("Datasource name has value: " + environment.getProperty("spring.datasource.username"));
        System.out.println("Datasource password has value: " + environment.getProperty("spring.datasource.password"));
        System.out.println("Datasource url has value: " + environment.getProperty("spring.datasource.url"));

        return "Just testing";
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    Logger.Level FeignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /*
    @Bean
    public FeignErrorDecoder getFeignErrorDecoder() {
        return new FeignErrorDecoder();
    }
    */
}

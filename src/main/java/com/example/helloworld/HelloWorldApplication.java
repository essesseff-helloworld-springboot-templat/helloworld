package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class HelloWorldApplication {
    
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldApplication.class);
    
    @Value("${server.port}")
    private String port;
    
    @Value("${app.environment}")
    private String environment;
    
    @Value("${app.version}")
    private String version;

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }
    
    @EventListener(ApplicationReadyEvent.class)
    public void logStartup() {
        logger.info("Starting Hello World Spring Boot server on port {}", port);
        logger.info("Environment: {}", environment);
        logger.info("Version: {}", version);
    }
}

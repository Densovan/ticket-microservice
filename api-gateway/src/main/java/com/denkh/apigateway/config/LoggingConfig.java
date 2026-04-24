package com.denkh.apigateway.config;


import com.denkh.apigateway.logging.GatewayLoggingProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

@Slf4j
@Configuration
@EnableWebFlux
@EnableConfigurationProperties(GatewayLoggingProperties.class)
public class LoggingConfig {

}
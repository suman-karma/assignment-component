package io.mhe.assignmentcomponent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//@Configuration
public class RestConfiguraiton {
    @Bean
    RestTemplate RestTemplate(){
        return new RestTemplate();
    }
}

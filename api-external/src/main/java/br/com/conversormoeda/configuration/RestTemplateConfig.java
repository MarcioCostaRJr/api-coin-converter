package br.com.conversormoeda.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * Configuration for to manipulate instance of RestTemplate
 *
 * @author mcrj
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Return a bean of RestTemplate
     *
     * @param builder - {@link RestTemplateBuilder}
     * @return {@link RestTemplate}
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .build();
    }

}

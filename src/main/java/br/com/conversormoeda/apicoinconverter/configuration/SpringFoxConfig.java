package br.com.conversormoeda.apicoinconverter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

import static java.lang.Boolean.FALSE;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

/**
 * Configuration for to manipulate instance of Swagger
 *
 * @author mcrj
 */
@Configuration
public class SpringFoxConfig {

    private static final String BASE_PACK = "br.com.conversormoeda.apicoinconverter.controller";
    private static final String BASE_PATH = "/transaction.*";

    private static final String TITLE = "API Coin Converter";
    private static final String DESCRIPTION = "Example project for to converter coin";
    private static final String VERSION = "0.0.1-SNAPSHOT";
    private static final String TERMS_OF_SERVICE = "Terms of service";
    private static final String CONTACT_NAME = "Marcio Júnior";
    private static final String CONTACT_URL = "https://github.com/MarcioCostaRJr/api-coin-converter";
    private static final String CONTACT_EMAIL = "marcio.c8@gmail.com";
    private static final String LICENSE = "Apache License Version 2.0";
    private static final String LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0";

    /**
     * Return a bean of Docket
     *
     * @return {@link Docket}
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(FALSE)
                .select()
                .apis(basePackage(BASE_PACK))
                .paths(regex(BASE_PATH))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfo(
                TITLE,
                DESCRIPTION,
                VERSION,
                TERMS_OF_SERVICE,
                new Contact(CONTACT_NAME, CONTACT_URL, CONTACT_EMAIL),
                LICENSE,
                LICENSE_URL,
                Collections.emptyList());
    }
}

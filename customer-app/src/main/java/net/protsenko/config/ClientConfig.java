package net.protsenko.config;

import net.protsenko.client.WebClientProductsClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Bean
    public WebClientProductsClientImpl webClientProductsClient(
            @Value("${selmag.services.catalogue.uri:http://localhost:8081}") String baseUrl
    ) {
        return new WebClientProductsClientImpl(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }

}

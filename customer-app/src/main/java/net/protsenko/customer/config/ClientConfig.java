package net.protsenko.customer.config;

import net.protsenko.customer.client.WebClientFavouriteProductsClientImpl;
import net.protsenko.customer.client.WebClientProductReviewsClientImpl;
import net.protsenko.customer.client.WebClientProductsClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {
    @Bean
    public WebClientProductsClientImpl webClientProductsClient(
            @Value("${selmag.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUrl
    ) {
        return new WebClientProductsClientImpl(WebClient.builder()
                .baseUrl(catalogueBaseUrl)
                .build());
    }

    @Bean
    public WebClientFavouriteProductsClientImpl webClientFavouriteProductsClient(
            @Value("${selmag.services.feedback.uri:http://localhost:8084}") String feedbackBaseUrl
    ) {
        return new WebClientFavouriteProductsClientImpl(WebClient.builder()
                .baseUrl(feedbackBaseUrl)
                .build());
    }

    @Bean
    public WebClientProductReviewsClientImpl webClientProductReviewsClient(
            @Value("${selmag.services.feedback.uri:http://localhost:8084}") String feedbackBaseUrl
    ) {
        return new WebClientProductReviewsClientImpl(WebClient.builder()
                .baseUrl(feedbackBaseUrl)
                .build());
    }
}

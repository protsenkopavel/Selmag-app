package net.protsenko.customer.config;

import net.protsenko.customer.client.WebClientFavouriteProductsClientImpl;
import net.protsenko.customer.client.WebClientProductReviewsClientImpl;
import net.protsenko.customer.client.WebClientProductsClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

import static org.mockito.Mockito.mock;

@Configuration
public class TestBeans {

    @Bean
    public ReactiveClientRegistrationRepository clientRegistrationRepository() {
        return mock();
    }

    @Bean
    public ServerOAuth2AuthorizedClientRepository authorizedClientRepository() {
        return mock();
    }

    @Bean
    @Primary
    public WebClientProductsClientImpl mockWebClientProductsClient() {
        return new WebClientProductsClientImpl(WebClient.builder()
                .baseUrl("http://localhost:54321")
                .build());
    }

    @Bean
    @Primary
    public WebClientFavouriteProductsClientImpl mockWebClientFavouriteProductsClient() {
        return new WebClientFavouriteProductsClientImpl(WebClient.builder()
                .baseUrl("http://localhost:54321")
                .build());
    }

    @Bean
    @Primary
    public WebClientProductReviewsClientImpl mockWebClientProductReviewsClient() {
        return new WebClientProductReviewsClientImpl(WebClient.builder()
                .baseUrl("http://localhost:54321")
                .build());
    }
}

package net.protsenko.customer.config;

import io.micrometer.observation.ObservationRegistry;
import net.protsenko.customer.client.WebClientFavouriteProductsClientImpl;
import net.protsenko.customer.client.WebClientProductReviewsClientImpl;
import net.protsenko.customer.client.WebClientProductsClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.DefaultClientRequestObservationConvention;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Bean
    @LoadBalanced
    @Scope("prototype")
    public WebClient.Builder selmagServicesWebClientBuilder(
            ReactiveClientRegistrationRepository clientRegistrationRepository,
            ServerOAuth2AuthorizedClientRepository authorizedClientRepository
//            ObservationRegistry observationRegistry
    ) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction filter =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository,
                        authorizedClientRepository);
        filter.setDefaultClientRegistrationId("keycloak");
        return WebClient.builder()
//                .observationRegistry(observationRegistry)
//                .observationConvention(new DefaultClientRequestObservationConvention())
                .filter(filter);
    }

    @Bean
    public WebClientProductsClientImpl webClientProductsClient(
            @Value("${selmag.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUrl,
            WebClient.Builder selmagServicesWebClientBuilder
    ) {
        return new WebClientProductsClientImpl(selmagServicesWebClientBuilder
                .baseUrl(catalogueBaseUrl)
                .build());
    }

    @Bean
    public WebClientFavouriteProductsClientImpl webClientFavouriteProductsClient(
            @Value("${selmag.services.feedback.uri:http://localhost:8084}") String feedbackBaseUrl,
            WebClient.Builder selmagServicesWebClientBuilder
    ) {
        return new WebClientFavouriteProductsClientImpl(selmagServicesWebClientBuilder
                .baseUrl(feedbackBaseUrl)
                .build());
    }

    @Bean
    public WebClientProductReviewsClientImpl webClientProductReviewsClient(
            @Value("${selmag.services.feedback.uri:http://localhost:8084}") String feedbackBaseUrl,
            WebClient.Builder selmagServicesWebClientBuilder
    ) {
        return new WebClientProductReviewsClientImpl(selmagServicesWebClientBuilder
                .baseUrl(feedbackBaseUrl)
                .build());
    }
}

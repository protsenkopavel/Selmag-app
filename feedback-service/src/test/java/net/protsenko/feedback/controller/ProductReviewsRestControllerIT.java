package net.protsenko.feedback.controller;

import net.protsenko.feedback.entity.ProductReview;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.UUID;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

@SpringBootTest
@AutoConfigureWebTestClient
class ProductReviewsRestControllerIT {

    @Autowired
    WebTestClient webClient;

    @Autowired
    ReactiveMongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        this.mongoTemplate.insertAll(List.of(
                        new ProductReview(UUID.fromString("3c851612-3261-11ef-9a95-e75757e38f66"), 1, 1, "Отзыв №1", "user-1"),
                        new ProductReview(UUID.fromString("3d100b5a-3261-11ef-aae5-fb43d4757134"), 1, 3, "Отзыв №2", "user-2"),
                        new ProductReview(UUID.fromString("3d7568ec-3261-11ef-9aa3-f375f1e32a82"), 1, 5, "Отзыв №3", "user-3")
                ))
                .blockLast();
    }

    @AfterEach
    void tearDown() {
        this.mongoTemplate.remove(ProductReview.class).all()
                .block();
    }

    @Test
    void findProductReviewsByProductId_ReturnsReviews() {
        // given

        // when
        this.webClient.mutateWith(mockJwt())
                .get()
                .uri("/feedback-api/product-reviews/by-product-id/1")
                .exchange()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .json("""
                        [
                          {"id": "3c851612-3261-11ef-9a95-e75757e38f66", "productId": 1, "rating": 1, "review": "Отзыв №1", "userId": "user-1"},
                          {"id": "3d100b5a-3261-11ef-aae5-fb43d4757134", "productId": 1, "rating": 3, "review": "Отзыв №2", "userId": "user-2"},
                          {"id": "3d7568ec-3261-11ef-9aa3-f375f1e32a82", "productId": 1, "rating": 5, "review": "Отзыв №3", "userId": "user-3"}
                          ]""");
        // then

    }

    @Test
    void createProductReview_RequestIsValid_ReturnsCreatedProductReview() {
        // given
        // when
        this.webClient
                .mutateWith(mockJwt().jwt(builder -> builder.subject("user-tester")))
                .post()
                .uri("/feedback-api/product-reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                        "productId": 1,
                        "rating": 5,
                        "review": "На пятерочку"
                        }
                        """)
        // then
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION)
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .json("""
                        {
                        "productId": 1,
                        "rating": 5,
                        "review": "На пятерочку",
                        "userId": "user-tester"
                        }
                        """).jsonPath("$.id").exists();
    }

}
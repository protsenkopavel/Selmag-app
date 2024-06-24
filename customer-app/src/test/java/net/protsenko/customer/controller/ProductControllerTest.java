package net.protsenko.customer.controller;

import net.protsenko.customer.client.FavouriteProductsClient;
import net.protsenko.customer.client.ProductReviewsClient;
import net.protsenko.customer.client.ProductsClient;
import net.protsenko.customer.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    ProductsClient productsClient;

    @Mock
    FavouriteProductsClient favouriteProductsClient;

    @Mock
    ProductReviewsClient productReviewsClient;

    @InjectMocks
    ProductController controller;

    @Test
    void loadProduct_ProductExists_ReturnsNotEmptyMono() {
        // given
        var product = new Product(1, "Товар №1", "Описание товара №1");
        doReturn(Mono.just(product)).when(this.productsClient).findProduct(1);

        // when
        StepVerifier.create(this.controller.loadProduct(1))
                // then
                .expectNext(new Product(1, "Товар №1", "Описание товара №1"))
                .expectComplete()
                .verify();

        verify(this.productsClient).findProduct(1);
        verifyNoMoreInteractions(this.productsClient);
        verifyNoInteractions(this.favouriteProductsClient, this.productReviewsClient);
    }

    @Test
    void loadProduct_ProductDoesNotExists_ReturnsMonoWithNoSuchElementException() {
        // given
        doReturn(Mono.empty()).when(this.productsClient).findProduct(1);

        // when
        StepVerifier.create(this.controller.loadProduct(1))
                // then
                .expectErrorMatches(exception -> {
                    return exception instanceof NoSuchElementException e && e.getMessage().equals("customer.products.error.not_found");
                })
                .verify();

        verify(this.productsClient).findProduct(1);
        verifyNoMoreInteractions(this.productsClient);
        verifyNoInteractions(this.favouriteProductsClient, this.productReviewsClient);
    }

    @Test
    void handleNoSuchElementException_ReturnsErrors404() {
        // given
        var exception = new NoSuchElementException("Товар не найден");
        var model = new ConcurrentModel();

        // when
        var result = this.controller.handleNoSuchElementException(exception, model);

        // then
        assertEquals("errors/404", result);
        assertEquals("Товар не найден", model.getAttribute("error"));
    }
}
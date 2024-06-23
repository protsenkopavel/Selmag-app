package net.protsenko.customer.controller.payload;

public record NewProductReviewPayload(
        Integer rating,
        String review) {
}

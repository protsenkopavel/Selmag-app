package net.protsenko.manager.controller;

import lombok.RequiredArgsConstructor;
import net.protsenko.manager.controller.payload.UpdateProductPayload;
import net.protsenko.manager.entity.Product;
import net.protsenko.manager.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("catalogue/products/{productId:\\d+}")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @ModelAttribute("product")
    public Product product(@PathVariable("productId") int productId) {
        return this.productService.findProduct(productId).orElseThrow();
    }

    @GetMapping
    public String getProduct() {
        return "catalogue/products/product";
    }

    @GetMapping("edit")
    public String getProductEditPage() {
        return "catalogue/products/edit";
    }

    @PostMapping("edit")
    public String updateProduct(@ModelAttribute("product") Product product, UpdateProductPayload payload) {
        this.productService.updateProduct(product.getId(), payload.title(), payload.details());
        return "redirect:/catalogue/products/%d".formatted(product.getId());
    }

    @PostMapping()
    public String deleteProduct(@ModelAttribute("product") Product product) {
        this.productService.deleteProduct(product.getId());
        return "redirect:/catalogue/products/list";
    }
}

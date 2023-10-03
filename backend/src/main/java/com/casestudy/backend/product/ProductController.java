package com.casestudy.backend.product;

import com.casestudy.backend.common.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAll();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);

        return ResponseEntity.ok(product);
    }

    @PutMapping("/dispense")
    public ResponseEntity<?> dispenseProduct() {
        List<Product> products = productService.getAll();

        return ResponseEntity.ok(new MessageResponse("TODO"));
    }
}

package com.casestudy.backend.vendingmachine.productstock;

import com.casestudy.backend.product.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/products")
public class ProductStockController {

    private final ProductStockService productStockService;

    public ProductStockController(final ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductStockQueryResponse>> getAllProducts() {
        List<ProductStock> productStocks = productStockService.getAllProducts();
        List<ProductStockQueryResponse> responseList = new ArrayList<>();

        for (ProductStock productStock : productStocks) {
            Product product = productStock.getProduct();
            responseList.add(new ProductStockQueryResponse(productStock.getId(), productStock.getCount(),
                    product.getId(), product.getName(), product.getPrice()));
        }

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductStockQueryResponse> getProductById(@PathVariable Long productId) {
        ProductStock productStock = productStockService.getProductById(productId);
        Product product = productStock.getProduct();

        return ResponseEntity.ok(new ProductStockQueryResponse(productStock.getId(), productStock.getCount(),
                product.getId(), product.getName(), product.getPrice()));
    }
}

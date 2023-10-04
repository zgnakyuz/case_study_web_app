package com.casestudy.backend.vendingmachine.productstock;

import com.casestudy.backend.common.response.ErrorResponse;
import com.casestudy.backend.common.response.MessageResponse;
import com.casestudy.backend.product.Product;
import org.springframework.http.HttpStatus;
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

    @PutMapping("/{productStockId}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addToStocks(@PathVariable Long productStockId, @RequestParam("quantity") int quantity) {
        try {
            final ProductStock productStock = productStockService.addToStocks(productStockId, quantity);
            return ResponseEntity.ok(new MessageResponse("%d %s added to stocks! New stock: %d".formatted(quantity,
                    productStock.getProduct().getName(), productStock.getCount())));
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
        }
    }

    @PutMapping("/prices/{productStockId}")
    public ResponseEntity<?> changeProductPrice(@PathVariable Long productStockId, @RequestParam("newPrice") int newPrice) {
        try {
            final ProductStock productStock = productStockService.changeProductPrice(productStockId, newPrice);
            return ResponseEntity.ok(new MessageResponse("The price of %s set as %d TL!"
                    .formatted(productStock.getProduct().getName(), newPrice)));
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
        }
    }
}

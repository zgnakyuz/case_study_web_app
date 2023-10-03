package com.casestudy.backend.vendingmachine.productstock;

import com.casestudy.backend.product.Product;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductStockService {

    @Autowired
    private ProductStockRepository productStockRepository;

    private static final String PRODUCT_STOCK_WITH_ID_DOES_NOT_EXIST_MESSAGE = "Product stock with id %s does not exist!";

    public List<ProductStock> getAllProducts() {
        return productStockRepository.findAll();
    }

    public ProductStock getProductById(Long id) {
        return productStockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_STOCK_WITH_ID_DOES_NOT_EXIST_MESSAGE.formatted(id)));
    }
}

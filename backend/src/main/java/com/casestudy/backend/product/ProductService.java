package com.casestudy.backend.product;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private static final String PRODUCT_WITH_ID_DOES_NOT_EXIST_MESSAGE = "Product with id %s does not exist!";

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_WITH_ID_DOES_NOT_EXIST_MESSAGE.formatted(id)));
    }
}

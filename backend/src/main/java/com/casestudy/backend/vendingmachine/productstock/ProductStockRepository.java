package com.casestudy.backend.vendingmachine.productstock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    Boolean existsByProductId(Long productId);
}

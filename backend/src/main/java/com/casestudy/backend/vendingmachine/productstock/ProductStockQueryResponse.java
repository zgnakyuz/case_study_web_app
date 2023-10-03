package com.casestudy.backend.vendingmachine.productstock;

import lombok.Getter;

@Getter
public class ProductStockQueryResponse {

    private final Long productStockId;

    private final Integer count;

    private final Long productId;

    private final String name;

    private final Integer price;

    public ProductStockQueryResponse(Long productStockId, Integer count, Long productId, String name, Integer price) {
        this.productStockId = productStockId;
        this.count = count;
        this.productId = productId;
        this.name = name;
        this.price = price;
    }
}

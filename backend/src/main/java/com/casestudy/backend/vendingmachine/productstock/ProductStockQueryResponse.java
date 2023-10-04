package com.casestudy.backend.vendingmachine.productstock;

import lombok.Getter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductStockQueryResponse that = (ProductStockQueryResponse) o;
        return Objects.equals(productStockId, that.productStockId) &&
                Objects.equals(count, that.count) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productStockId, count, productId, name, price);
    }
}

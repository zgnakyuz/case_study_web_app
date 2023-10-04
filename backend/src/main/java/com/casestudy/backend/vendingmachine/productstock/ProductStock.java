package com.casestudy.backend.vendingmachine.productstock;

import com.casestudy.backend.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_stock")
@Getter
@Setter
@NoArgsConstructor
public class ProductStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Min(0)
    @Max(20)
    private Integer count;

    public ProductStock(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    public void updateCount(int count) {
        this.count = count;
    }
}

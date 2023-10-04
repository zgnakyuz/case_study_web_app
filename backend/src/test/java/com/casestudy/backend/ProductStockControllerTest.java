package com.casestudy.backend;

import com.casestudy.backend.common.response.ErrorResponse;
import com.casestudy.backend.common.response.MessageResponse;
import com.casestudy.backend.product.Product;
import com.casestudy.backend.vendingmachine.productstock.ProductStock;
import com.casestudy.backend.vendingmachine.productstock.ProductStockController;
import com.casestudy.backend.vendingmachine.productstock.ProductStockQueryResponse;
import com.casestudy.backend.vendingmachine.productstock.ProductStockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ProductStockControllerTest {

    @InjectMocks
    private ProductStockController productStockController;

    @Mock
    private ProductStockService productStockService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        List<ProductStock> productStocks = new ArrayList<>();
        Product product1 = new Product("Pname1", 1);
        product1.setId(1L);
        ProductStock productStock1 = new ProductStock(product1, 2);
        productStock1.setId(1L);
        productStocks.add(productStock1);
        Product product2 = new Product("Pname2", 3);
        product2.setId(2L);
        ProductStock productStock2 = new ProductStock(product2, 4);
        productStock2.setId(2L);
        productStocks.add(productStock2);

        List<ProductStockQueryResponse> responseList = new ArrayList<>();

        for (ProductStock productStock : productStocks) {
            Product product = productStock.getProduct();
            responseList.add(new ProductStockQueryResponse(productStock.getId(), productStock.getCount(),
                    product.getId(), product.getName(), product.getPrice()));
        }

        when(productStockService.getAllProducts()).thenReturn(productStocks);

        ResponseEntity<List<ProductStockQueryResponse>> response = productStockController.getAllProducts();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseList, response.getBody());
    }

    @Test
    public void testGetProductById() {
        Product product = new Product("Pname", 1);
        product.setId(1L);
        ProductStock productStock = new ProductStock(product, 2);
        productStock.setId(1L);

        when(productStockService.getProductById(productStock.getId())).thenReturn(productStock);

        ResponseEntity<ProductStockQueryResponse> response = productStockController.getProductById(productStock.getId());

        assertEquals(200, response.getStatusCode().value());
        assertEquals(new ProductStockQueryResponse(productStock.getId(), productStock.getCount(),
                product.getId(), product.getName(), product.getPrice()), response.getBody());
    }

    @Test
    public void testAddToStocks() {
        Product product = new Product("Pname", 1);
        product.setId(1L);
        ProductStock productStock = new ProductStock(product, 2);
        productStock.setId(1L);
        int quantity = 10;

        when(productStockService.addToStocks(productStock.getId(), quantity)).thenReturn(productStock);

        ResponseEntity<?> response = productStockController.addToStocks(productStock.getId(), quantity);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(new MessageResponse("%d %s added to stocks! New stock: %d".formatted(quantity,
                product.getName(), productStock.getCount())), response.getBody());
    }

    @Test
    public void testChangeProductPrice() throws Exception {
        Product product = new Product("Pname", 1);
        product.setId(1L);
        ProductStock productStock = new ProductStock(product, 2);
        productStock.setId(1L);
        int newPrice = 20;

        when(productStockService.changeProductPrice(productStock.getId(), newPrice)).thenReturn(productStock);

        ResponseEntity<?> response = productStockController.changeProductPrice(productStock.getId(), newPrice);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(new MessageResponse("The price of %s set as %d TL!"
                .formatted(productStock.getProduct().getName(), newPrice)), response.getBody());
    }
}

package com.casestudy.backend.vendingmachine;

import com.casestudy.backend.common.enums.CoinType;
import com.casestudy.backend.product.Product;
import com.casestudy.backend.vendingmachine.productstock.ProductStock;

import java.util.List;

public interface VendingMachineService {

    public void start();
    public VendingMachine getState();
    public void saveState(VendingMachine vendingMachine);

    public long selectItemAndGetPrice(Product product);

    public void insertCoin(CoinType coin);

    public List<CoinType> refund();

    public void reset();
}

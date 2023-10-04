package com.casestudy.backend.vendingmachine;

import com.casestudy.backend.common.enums.CoinType;
import com.casestudy.backend.product.Product;
import com.casestudy.backend.user.AdminPanelResponse;
import com.casestudy.backend.vendingmachine.productstock.ProductStock;
import org.springframework.data.util.Pair;

import java.util.List;

public interface VendingMachineService {

    public void start();
    public VendingMachine getState();
    public void saveState(VendingMachine vendingMachine);

    public Pair<String, Integer> dispenseProductAndReturnChange(Long productStockId, Long userId) throws Exception;

    public void insertCoin(CoinType coin, Long userId) throws Exception;

    public int refund(Long userId);

    public void reset();

    public int collectMoney(Long userId);

    public AdminPanelResponse getAdminPanelInfo();
}

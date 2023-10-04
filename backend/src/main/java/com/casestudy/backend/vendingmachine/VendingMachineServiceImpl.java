package com.casestudy.backend.vendingmachine;

import com.casestudy.backend.common.enums.CoinType;
import com.casestudy.backend.user.User;
import com.casestudy.backend.user.UserService;
import com.casestudy.backend.vendingmachine.productstock.ProductStock;
import com.casestudy.backend.vendingmachine.productstock.ProductStockService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendingMachineServiceImpl implements VendingMachineService {

    private VendingMachine vendingMachine;

    private final VendingMachineRepository vendingMachineRepository;

    private final UserService userService;

    private final ProductStockService productStockService;

    public VendingMachineServiceImpl(final VendingMachineRepository vendingMachineRepository,
                                     final UserService userService, ProductStockService productStockService) {
        this.vendingMachineRepository = vendingMachineRepository;
        this.userService = userService;
        this.productStockService = productStockService;
    }

    @Override
    public void start() {
        VendingMachine vendingMachine = getState();
        saveState(vendingMachine);
    }

    @Override
    public VendingMachine getState() {
        if (vendingMachine == null) {  // Singleton approach
            vendingMachine = loadStateFromDatabase();
        }
        return vendingMachine;
    }

    @Override
    public void saveState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        saveStateToDatabase(vendingMachine);
    }

    private VendingMachine loadStateFromDatabase() {
        // Load the state from the database, or create a new state if it doesn't exist
        VendingMachine savedState = vendingMachineRepository.findById(1L).orElse(null);
        if (savedState == null) {
            savedState = new VendingMachine(0, 0);
        }
        return savedState;
    }

    private void saveStateToDatabase(VendingMachine state) {
        vendingMachineRepository.save(state);
    }

    @Override
    public Pair<String, Integer> dispenseProductAndReturnChange(Long productStockId, Long userId) throws Exception {
        final ProductStock productInStock = productStockService.isProductInStock(productStockId);
        int change;

        if (productInStock.getProduct() != null) {
            int price = productInStock.getProduct().getPrice();
            change = vendingMachine.getTempMoney() - price;
            vendingMachine.updateTempMoney(0);
            vendingMachine.updateTotalMoney(vendingMachine.getTotalMoney() + price);
            productStockService.dispenseProduct(productStockId);
            userService.addMoney(change, userId);
        } else {
            throw new Exception("Product is out of stock!");
        }

        saveState(vendingMachine);
        return Pair.of(productInStock.getProduct().getName(), change);
    }

    @Override
    public void insertCoin(CoinType coin, Long userId) throws Exception {
        final int coinValue = coin.getValue();
        userService.withdrawMoney(coinValue, userId);
        vendingMachine.updateTempMoney(vendingMachine.getTempMoney() + coinValue);
        saveState(vendingMachine);
    }

    @Override
    public int refund(Long userId) {
        int machineTempMoney = vendingMachine.getTempMoney();
        vendingMachine.updateTempMoney(0);
        userService.addMoney(machineTempMoney, userId);

        saveState(vendingMachine);
        return machineTempMoney;
    }

    @Override
    public void reset() {

    }
}

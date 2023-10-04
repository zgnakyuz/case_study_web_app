package com.casestudy.backend.vendingmachine;

import com.casestudy.backend.common.enums.CoinType;
import com.casestudy.backend.user.UserService;
import com.casestudy.backend.vendingmachine.productstock.ProductStock;
import com.casestudy.backend.vendingmachine.productstock.ProductStockService;
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
    public String dispenseProduct(Long productStockId) throws Exception {
        final ProductStock productInStock = productStockService.isProductInStock(productStockId);

        if (productInStock.getProduct() != null) {
            int price = productInStock.getProduct().getPrice();
            vendingMachine.updateTempMoney(vendingMachine.getTempMoney() - price);
            vendingMachine.updateTotalMoney(vendingMachine.getTotalMoney() + price);
            productStockService.dispenseProduct(productStockId);
        } else {
            throw new Exception("Product is out of stock!");
        }

        saveState(vendingMachine);
        return productInStock.getProduct().getName();
    }

    @Override
    public void insertCoin(CoinType coin, Long userId) throws Exception {
        final int coinValue = coin.getValue();
        userService.withdrawMoney(coinValue, userId);
        vendingMachine.updateTempMoney(vendingMachine.getTempMoney() + coinValue);
        saveState(vendingMachine);
    }

    @Override
    public List<CoinType> refund() {
        return null;
    }

    @Override
    public void reset() {

    }
}

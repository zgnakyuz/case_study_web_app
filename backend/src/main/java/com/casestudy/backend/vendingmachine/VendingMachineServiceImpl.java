package com.casestudy.backend.vendingmachine;

import com.casestudy.backend.common.enums.CoinType;
import com.casestudy.backend.product.Product;
import com.casestudy.backend.user.User;
import com.casestudy.backend.user.UserRepository;
import com.casestudy.backend.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendingMachineServiceImpl implements VendingMachineService {

    private VendingMachine vendingMachine;

    private final VendingMachineRepository vendingMachineRepository;

    private final UserService userService;

    public VendingMachineServiceImpl(final VendingMachineRepository vendingMachineRepository,
                                     final UserService userService) {
        this.vendingMachineRepository = vendingMachineRepository;
        this.userService = userService;
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
    public void dispenseProduct(Product product) {
    }

    @Override
    public void insertCoin(CoinType coin, Long userId) throws Exception {
        int coinValue = coin.getValue();
        userService.withdrawMoney(coinValue, userId);
        vendingMachine.addCoinToTempMoney(coinValue);
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

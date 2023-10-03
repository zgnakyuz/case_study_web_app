package com.casestudy.backend.vendingmachine;

import com.casestudy.backend.common.enums.CoinType;
import com.casestudy.backend.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendingMachineServiceImpl implements VendingMachineService {

    private VendingMachine vendingMachine;

    @Autowired
    private VendingMachineRepository vendingMachineRepository;

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
    public long selectItemAndGetPrice(Product product) {
        return 0;
    }

    @Override
    public void insertCoin(CoinType coin) {

    }

    @Override
    public List<CoinType> refund() {
        return null;
    }

    @Override
    public void reset() {

    }
}

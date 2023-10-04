package com.casestudy.backend;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.casestudy.backend.user.User;
import com.casestudy.backend.user.UserRepository;
import com.casestudy.backend.user.UserService;
import com.casestudy.backend.vendingmachine.VendingMachine;
import com.casestudy.backend.vendingmachine.VendingMachineRepository;
import com.casestudy.backend.vendingmachine.VendingMachineServiceImpl;
import com.casestudy.backend.vendingmachine.productstock.ProductStockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class VendingMachineServiceImplTest {

    private VendingMachineServiceImpl vendingMachineService;
    private VendingMachine vendingMachine;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        VendingMachineRepository vendingMachineRepository = mock(VendingMachineRepository.class);
        ProductStockService productStockService = mock(ProductStockService.class);
        vendingMachine = new VendingMachine(0, 0);
        userService = new UserService(mock(UserRepository.class));
        vendingMachineService = new VendingMachineServiceImpl(vendingMachineRepository, userService, productStockService);
    }

    @Test
    public void testRefund() {
        int initialTempMoney = 50;
        int initialUserMoney = 10;
        Long userId = 1L;

        VendingMachine mockVendingMachine = mock(VendingMachine.class);

        when(mockVendingMachine.getTempMoney()).thenReturn(initialTempMoney);

        UserService mockUserService = mock(UserService.class);

        User user = new User();
        user.setId(userId);
        user.updateMoney(initialUserMoney);
        when(mockUserService.getUserById(userId)).thenReturn(user);
        doAnswer(invocation -> {
            user.updateMoney(user.getMoney() + initialTempMoney);

            return null;
        }).when(mockUserService).addMoney(eq(initialTempMoney), eq(userId));

        VendingMachineServiceImpl vendingMachineService = new VendingMachineServiceImpl(
                mock(VendingMachineRepository.class),
                mockUserService,
                mock(ProductStockService.class)
        );
        mockVendingMachine.setTempMoney(initialTempMoney);
        vendingMachineService.saveState(mockVendingMachine);

        int refundedAmount = vendingMachineService.refund(userId);

        assertEquals(initialTempMoney, refundedAmount);
        assertEquals(0, vendingMachine.getTempMoney());
        assertEquals(initialTempMoney + initialUserMoney, user.getMoney());
    }

}

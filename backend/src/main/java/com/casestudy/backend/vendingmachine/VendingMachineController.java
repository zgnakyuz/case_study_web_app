package com.casestudy.backend.vendingmachine;

import com.casestudy.backend.product.Product;
import com.casestudy.backend.vendingmachine.productstock.ProductStock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class VendingMachineController {

    private final VendingMachineServiceImpl vendingMachineService;

    public VendingMachineController(VendingMachineServiceImpl vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
    }
}

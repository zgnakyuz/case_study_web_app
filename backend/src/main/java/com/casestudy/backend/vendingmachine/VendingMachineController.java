package com.casestudy.backend.vendingmachine;

import com.casestudy.backend.common.enums.CoinType;
import com.casestudy.backend.common.response.ErrorResponse;
import com.casestudy.backend.common.response.MessageResponse;
import com.casestudy.backend.product.Product;
import com.casestudy.backend.user.AdminPanelResponse;
import com.casestudy.backend.vendingmachine.productstock.ProductStock;
import com.casestudy.backend.vendingmachine.productstock.ProductStockQueryResponse;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/machine")
public class VendingMachineController {

    private final VendingMachineServiceImpl vendingMachineService;

    public VendingMachineController(final VendingMachineServiceImpl vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
    }

    @GetMapping
    public ResponseEntity<?> getMachineState() {
        return ResponseEntity.ok(vendingMachineService.getState());
    }

    @PutMapping("/balance/{coinType}")
    public ResponseEntity<?> insertCoin(@PathVariable CoinType coinType, @RequestParam("userId") Long userId) {
        try {
            vendingMachineService.insertCoin(coinType, userId);
            return ResponseEntity.ok(new MessageResponse("%s TL inserted successfully!".formatted(coinType.getValue())));
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
        }
    }

    @PutMapping("/balance")
    public ResponseEntity<?> refund(@RequestParam("userId") Long userId) {
        try {
            final int refundedMoney = vendingMachineService.refund(userId);
            return ResponseEntity.ok(new MessageResponse("%s TL refunded successfully!".formatted(refundedMoney)));
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
        }
    }

    @PutMapping("/productStock/{productStockId}")
    public ResponseEntity<?> dispenseProductAndReturnChange(@PathVariable Long productStockId, @RequestParam("userId") Long userId) {
        try {
            final Pair<String, Integer> dispensedProductAndChange = vendingMachineService.dispenseProductAndReturnChange(productStockId, userId);
            return ResponseEntity.ok(new MessageResponse("%s purchased, %d TL change returned"
                    .formatted(dispensedProductAndChange.getFirst(),
                            dispensedProductAndChange.getSecond())));
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
        }
    }

    @PutMapping("/reset")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> reset() {
        try {
            vendingMachineService.reset();
            return ResponseEntity.ok(new MessageResponse("Machine reset has done!"));
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
        }
    }

    @PutMapping("/cashdrawer")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> collectMoney(@RequestParam("userId") Long userId) {
        try {
            final int collected = vendingMachineService.collectMoney(userId);
            return ResponseEntity.ok(new MessageResponse("%d TL has collected!".formatted(collected)));
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
        }
    }

//    @GetMapping("/info")
//    //@PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<AdminPanelResponse> getAdminPanelInfo() {
//        AdminPanelResponse adminPanelResponse = vendingMachineService.getAdminPanelInfo();
//        return ResponseEntity.ok(adminPanelResponse);
//    }
}

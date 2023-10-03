package com.casestudy.backend.user;

import com.casestudy.backend.product.Product;
import com.casestudy.backend.vendingmachine.productstock.ProductStock;
import com.casestudy.backend.vendingmachine.productstock.ProductStockQueryResponse;
import com.casestudy.backend.vendingmachine.productstock.ProductStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);

        return ResponseEntity.ok(user);
    }
}

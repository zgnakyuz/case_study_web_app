package com.casestudy.backend.user;

import com.casestudy.backend.product.Product;
import com.casestudy.backend.vendingmachine.productstock.ProductStock;
import com.casestudy.backend.vendingmachine.productstock.ProductStockQueryResponse;
import com.casestudy.backend.vendingmachine.productstock.ProductStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserQueryResponse> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        List<String> roles = user.getRoles().stream().map(role -> role.getUserType().name()).toList();
        UserQueryResponse response = new UserQueryResponse(user.getId(), user.getUsername(), user.getEmail(),
                user.getMoney(), roles);

        return ResponseEntity.ok(response);
    }
}

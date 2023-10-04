package com.casestudy.backend.user;

import com.casestudy.backend.vendingmachine.productstock.ProductStock;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void withdrawMoney(int coinValue, Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(""));
        if (user.getMoney() < coinValue) {
            throw new Exception("User does not have %s TL to insert".formatted(coinValue));
        }
        user.updateMoney(user.getMoney() - coinValue);
        userRepository.save(user);
    }

    public void addMoney(int money, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(""));
        user.updateMoney(user.getMoney() + money);
        userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(""));
    }
}

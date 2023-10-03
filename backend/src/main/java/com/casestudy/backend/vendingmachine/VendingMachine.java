package com.casestudy.backend.vendingmachine;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vending_machine_state")
@Getter
@Setter
@NoArgsConstructor
public class VendingMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer userBalance;

    private Integer totalMoney;

    public VendingMachine(Integer userBalance, Integer totalMoney) {
        this.userBalance = userBalance;
        this.totalMoney = totalMoney;
    }
}

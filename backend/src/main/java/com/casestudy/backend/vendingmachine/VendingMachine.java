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

    private Integer tempMoney;

    private Integer totalMoney;

    public VendingMachine(Integer tempMoney, Integer totalMoney) {
        this.tempMoney = tempMoney;
        this.totalMoney = totalMoney;
    }

    public void addCoinToTempMoney(Integer coin) {
        tempMoney += coin;
    }
}

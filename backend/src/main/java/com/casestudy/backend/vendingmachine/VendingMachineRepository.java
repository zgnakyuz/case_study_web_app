package com.casestudy.backend.vendingmachine;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VendingMachineRepository extends JpaRepository<VendingMachine, Long> {
}

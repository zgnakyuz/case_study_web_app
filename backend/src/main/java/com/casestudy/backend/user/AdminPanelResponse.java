package com.casestudy.backend.user;

public class AdminPanelResponse {

    private final int totalMoney;

    private final int totalProductCountInStock;

    public AdminPanelResponse(int totalMoney, int totalProductCountInStock) {
        this.totalMoney = totalMoney;
        this.totalProductCountInStock = totalProductCountInStock;
    }
}

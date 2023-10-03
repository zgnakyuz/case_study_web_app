package com.casestudy.backend.common.enums;

import lombok.Getter;

@Getter
public enum CoinType {

    ONE(1), FIVE(5), TEN(10), TWENTY(20);
    private final int value;

    private CoinType(int value) {
        this.value = value;
    }
}

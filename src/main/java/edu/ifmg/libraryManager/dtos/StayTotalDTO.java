package edu.ifmg.libraryManager.dtos;

import java.math.BigDecimal;

public class StayTotalDTO {
    private Long userId;
    private BigDecimal totalAmount;

    public StayTotalDTO(Long userId, BigDecimal totalAmount) {
        this.userId = userId;
        this.totalAmount = totalAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}

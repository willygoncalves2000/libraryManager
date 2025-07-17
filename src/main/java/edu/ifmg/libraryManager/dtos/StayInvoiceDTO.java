package edu.ifmg.libraryManager.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class StayInvoiceDTO {
    private LocalDate checkInDate;
    private String roomDescription;
    private BigDecimal amount;

    public StayInvoiceDTO(LocalDate checkInDate, String roomDescription, BigDecimal amount) {
        this.checkInDate = checkInDate;
        this.roomDescription = roomDescription;
        this.amount = amount;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

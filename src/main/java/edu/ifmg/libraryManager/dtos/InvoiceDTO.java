package edu.ifmg.libraryManager.dtos;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceDTO {
    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private List<StayInvoiceDTO> stays;
    private BigDecimal totalAmount;

    public InvoiceDTO(String clientName, String clientEmail, String clientPhone, List<StayInvoiceDTO> stays, BigDecimal totalAmount) {
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientPhone = clientPhone;
        this.stays = stays;
        this.totalAmount = totalAmount;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public List<StayInvoiceDTO> getStays() {
        return stays;
    }

    public void setStays(List<StayInvoiceDTO> stays) {
        this.stays = stays;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}

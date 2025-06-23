package edu.ifmg.hotelBAO.dtos;

import edu.ifmg.hotelBAO.entities.Room;

import java.math.BigDecimal;

public class RoomDTO {
    private Long id;
    private String description;
    private BigDecimal price;
    private String imageUrl;

    public RoomDTO() {
    }

    public RoomDTO(Long id, String description, BigDecimal price, String imageUrl) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public RoomDTO(Room entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imageUrl = entity.getImageUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

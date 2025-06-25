package edu.ifmg.hotelBAO.dtos;

import edu.ifmg.hotelBAO.entities.Stay;

import java.time.LocalDate;

public class StayDTO {

    private Long id;
    private Long userId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public StayDTO() {}

    public StayDTO(Long id, Long userId, Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public StayDTO(Stay entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.roomId = entity.getRoom().getId();
        this.checkInDate = entity.getCheckInDate();
        this.checkOutDate = entity.getCheckOutDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}

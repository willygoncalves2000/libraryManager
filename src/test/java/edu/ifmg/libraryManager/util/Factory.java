package edu.ifmg.libraryManager.util;

import java.math.BigDecimal;

public class Factory {
    public static Room createRoom() {
        Room room = new Room();
        room.setName("Room 1");
        room.setDescription("Room 1 Description");
        room.setImageUrl("Room 1 Image");
        room.setPrice(BigDecimal.valueOf(100));
        return room;
    }
    public static RoomDTO createRoomDTO() {
        Room r = createRoom();
        return new RoomDTO(r);
    }
}

package edu.ifmg.hotelBAO.repository;

import edu.ifmg.hotelBAO.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}

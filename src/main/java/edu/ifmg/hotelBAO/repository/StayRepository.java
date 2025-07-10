package edu.ifmg.hotelBAO.repository;

import edu.ifmg.hotelBAO.entities.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StayRepository extends JpaRepository<Stay, Long> {

    List<Stay> findByUserId(Long userId);
}

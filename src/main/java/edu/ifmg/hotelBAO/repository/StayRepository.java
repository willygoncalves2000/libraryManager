package edu.ifmg.hotelBAO.repository;

import edu.ifmg.hotelBAO.entities.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StayRepository extends JpaRepository<Stay, Long> {

}

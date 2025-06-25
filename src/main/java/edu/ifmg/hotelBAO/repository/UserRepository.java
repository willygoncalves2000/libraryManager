package edu.ifmg.hotelBAO.repository;

import edu.ifmg.hotelBAO.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}

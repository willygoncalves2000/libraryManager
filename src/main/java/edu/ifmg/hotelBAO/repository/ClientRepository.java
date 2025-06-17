package edu.ifmg.hotelBAO.repository;

import edu.ifmg.hotelBAO.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {


}

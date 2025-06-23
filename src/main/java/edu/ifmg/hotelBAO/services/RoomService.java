package edu.ifmg.hotelBAO.services;

import edu.ifmg.hotelBAO.dtos.ClientDTO;
import edu.ifmg.hotelBAO.dtos.RoomDTO;
import edu.ifmg.hotelBAO.entities.Client;
import edu.ifmg.hotelBAO.entities.Room;
import edu.ifmg.hotelBAO.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository repository;

    public List<RoomDTO> findAll() {
        List<Room> list = repository.findAll();
        return list.stream().map(x -> new RoomDTO(x)).collect(Collectors.toList());
    }

}

package edu.ifmg.hotelBAO.resources;

import edu.ifmg.hotelBAO.dtos.ClientDTO;
import edu.ifmg.hotelBAO.dtos.RoomDTO;
import edu.ifmg.hotelBAO.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/room")
public class RoomResource {

    @Autowired
    private RoomService service;

    @GetMapping
    public ResponseEntity<List<RoomDTO>> findAll() {
        List<RoomDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
}

package edu.ifmg.hotelBAO.resources;

import edu.ifmg.hotelBAO.dtos.RoomDTO;
import edu.ifmg.hotelBAO.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<RoomDTO> insert(@RequestBody RoomDTO dto) {
        RoomDTO room = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(room.getId()).toUri();
        return ResponseEntity.created(uri).body(room);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RoomDTO> update(@PathVariable Long id, @RequestBody RoomDTO dto) {
        RoomDTO room = service.update(id, dto);
        return ResponseEntity.ok().body(room);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<RoomDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

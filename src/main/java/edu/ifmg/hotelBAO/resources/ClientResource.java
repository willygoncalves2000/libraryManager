package edu.ifmg.hotelBAO.resources;

import edu.ifmg.hotelBAO.dtos.ClientDTO;
import edu.ifmg.hotelBAO.dtos.ClientInsertDTO;
import edu.ifmg.hotelBAO.entities.Client;
import edu.ifmg.hotelBAO.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

    @Autowired
    private ClientService service;
    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() {
        List<ClientDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<ClientDTO> insert(@Valid @RequestBody ClientInsertDTO dto) {
        ClientDTO client = clientService.insert(dto);
        return ResponseEntity.ok().body(client);
    }
}

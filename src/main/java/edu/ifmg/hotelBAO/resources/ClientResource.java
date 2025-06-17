package edu.ifmg.hotelBAO.resources;

import edu.ifmg.hotelBAO.dtos.ClientDTO;
import edu.ifmg.hotelBAO.entities.Client;
import edu.ifmg.hotelBAO.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

    @Autowired
    private ClientService service;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() {
        List<ClientDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
}

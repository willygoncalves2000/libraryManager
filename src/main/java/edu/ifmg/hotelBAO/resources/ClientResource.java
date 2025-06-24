package edu.ifmg.hotelBAO.resources;

import edu.ifmg.hotelBAO.dtos.ClientDTO;
import edu.ifmg.hotelBAO.dtos.ClientInsertDTO;
import edu.ifmg.hotelBAO.entities.Client;
import edu.ifmg.hotelBAO.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/client")
public class ClientResource {

    @Autowired
    private ClientService service;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() {
        List<ClientDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<ClientDTO> insert(@Valid @RequestBody ClientInsertDTO dto) {
        ClientDTO client = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(client.getId())
                .toUri();
        return ResponseEntity.created(uri).body(client);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ClientDTO> update(@PathVariable long id, @Valid @RequestBody ClientDTO dto) {
        return ResponseEntity.ok().body(service.update(id, dto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}

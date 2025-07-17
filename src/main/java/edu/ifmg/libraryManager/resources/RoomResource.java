package edu.ifmg.libraryManager.resources;

import edu.ifmg.libraryManager.dtos.RoomDTO;
import edu.ifmg.libraryManager.services.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/room")
@Tag(name = "Room", description = "Controller for Rooms")
public class RoomResource {

    @Autowired
    private RoomService service;


    @Operation(
            description = "Get all Rooms",
            summary = "List all Rooms",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Bad Request", responseCode = "400")
            }
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<RoomDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "3") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "description") String orderBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        Page<RoomDTO> rooms = service.findAll(pageable);
        return ResponseEntity.ok().body(rooms);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            description = "Create a new Room",
            summary = "Creates a new Room when authenticated as ADMIN",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422"),
            }
    )
    @PostMapping(produces = "application/json")
    public ResponseEntity<RoomDTO> insert(@RequestBody RoomDTO dto) {
        RoomDTO room = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(room.getId()).toUri();
        return ResponseEntity.created(uri).body(room);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            description = "Update a Room",
            summary = "Update a existing, Room when authenticated as ADMIN",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422"),
            }
    )
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<RoomDTO> update(@PathVariable Long id, @RequestBody RoomDTO dto) {
        RoomDTO room = service.update(id, dto);
        return ResponseEntity.ok().body(room);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            description = "Delete a Room",
            summary = "Delete a existing Room, when authenticated as ADMIN",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422"),
            }
    )
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<RoomDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(
            description = "Get a Room",
            summary = "List only one Room",
            responses = {
                    @ApiResponse(description = "ok", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404"),

            }
    )
    public ResponseEntity<RoomDTO> findById(@PathVariable Long id) {
        RoomDTO room = service.findById(id);
        return ResponseEntity.ok().body(room);
    }
}

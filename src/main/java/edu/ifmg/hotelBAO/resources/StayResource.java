package edu.ifmg.hotelBAO.resources;

import edu.ifmg.hotelBAO.dtos.StayDTO;
import edu.ifmg.hotelBAO.services.StayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/stay")
@Tag(name = "Stay", description = "Controller for Stays")
public class StayResource {

    @Autowired
    private StayService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "List all stays",
            description = "Retrieves all stays. Accessible only by ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of stays returned",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StayDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @GetMapping
    public ResponseEntity<List<StayDTO>> findAll() {
        List<StayDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
    @Operation(
            summary = "Create a new stay",
            description = "Creates a new stay and returns it with its generated ID. Accessible by CLIENT and ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Stay created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StayDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid data"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping
    public ResponseEntity<StayDTO> insert(@RequestBody StayDTO dto) {
        StayDTO stay = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(stay.getId()).toUri();
        return ResponseEntity.created(uri).body(stay);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Update a stay",
            description = "Updates the stay with the specified ID. Accessible only by ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stay updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StayDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid data"),
                    @ApiResponse(responseCode = "404", description = "Stay not found"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<StayDTO> update(@Parameter(description = "ID of the stay to update") @PathVariable Long id, @RequestBody StayDTO dto) {
        StayDTO stay = service.update(id, dto);
        return ResponseEntity.ok().body(stay);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Delete a stay",
            description = "Deletes the stay with the specified ID. Accessible only by ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Stay deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Stay not found"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StayDTO> delete( @Parameter(description = "ID of the stay to delete") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }





}

package edu.ifmg.hotelBAO.resources;

import edu.ifmg.hotelBAO.dtos.UserDTO;
import edu.ifmg.hotelBAO.dtos.UserInsertDTO;
import edu.ifmg.hotelBAO.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@Tag(name = "User", description = "Controller for Users")
public class UserResource {

    @Autowired
    private UserService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "List all users",
            description = "Retrieves all users. Only accessible by ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of users returned",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @Operation(
            summary = "Create a new user",
            description = "Creates a user and returns it with its generated ID",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid data"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping(produces = "application/json")
    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO dto) {
        UserDTO user = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
    @Operation(
            summary = "Update a user",
            description = "Updates the user with the specified ID. Accessible by ADMIN and CLIENT.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid data"),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserDTO> update(@Parameter(description = "ID of the user to update") @PathVariable long id, @Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok().body(service.update(id, dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(
            summary = "Delete a user",
            description = "Deletes the user with the specified ID. Only accessible by ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID of the user to delete") @PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}

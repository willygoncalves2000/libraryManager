package edu.ifmg.hotelBAO.resources;

import edu.ifmg.hotelBAO.dtos.InvoiceDTO;
import edu.ifmg.hotelBAO.dtos.StayInvoiceDTO;
import edu.ifmg.hotelBAO.dtos.StayTotalDTO;
import edu.ifmg.hotelBAO.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoice")
@Tag(name = "Invoice", description = "Controller for Invoices")
public class InvoiceResource {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
    @Operation(
            summary = "Generate invoice for a user",
            description = "Generates a detailed invoice with all stays of the specified user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Invoice generated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = InvoiceDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid user or stay data"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/{userId}")
    public ResponseEntity<?> getInvoice(@Parameter(description = "User ID to generate invoice for", example = "1")  @PathVariable Long userId) {
        try {
            InvoiceDTO invoice = userService.generateInvoice(userId);
            return ResponseEntity.ok(invoice);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
    @Operation(
            summary = "Get total stay amount",
            description = "Calculates the total value of all stays for a specific user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Total calculated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StayTotalDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/total/{userId}")
    public ResponseEntity<StayTotalDTO> getTotalStayAmount(@Parameter(description = "User ID", example = "1")  @PathVariable Long userId) {
        return ResponseEntity.ok(userService.calculateTotalStayAmount(userId));
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
    @Operation(
            summary = "Get cheapest stay",
            description = "Returns the cheapest stay of the user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cheapest stay returned",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StayInvoiceDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Stay not found")
            }
    )
    @GetMapping("/cheapest/{userId}")
    public ResponseEntity<StayInvoiceDTO> getCheapestStay(@Parameter(description = "User ID", example = "1")  @PathVariable Long userId) {
        return ResponseEntity.ok(userService.findCheapestStay(userId));
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
    @Operation(
            summary = "Get most expensive stay",
            description = "Returns the most expensive stay of the user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Most expensive stay returned",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StayInvoiceDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Stay not found")
            }
    )
    @GetMapping("/most-expensive/{userId}")
    public ResponseEntity<StayInvoiceDTO> getMostExpensiveStay(@Parameter(description = "User ID", example = "1") @PathVariable Long userId) {
        return ResponseEntity.ok(userService.findMostExpensiveStay(userId));
    }
}

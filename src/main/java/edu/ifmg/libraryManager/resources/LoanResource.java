package edu.ifmg.libraryManager.resources;

import edu.ifmg.libraryManager.dtos.LoanDTO;
import edu.ifmg.libraryManager.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanResource {

    @Autowired
    private LoanService loanService;


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENTE')")
    @GetMapping
    public ResponseEntity<List<LoanDTO>> findAll() {
        List<LoanDTO> list = loanService.findAll();
        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> findById(@PathVariable Long id) {
        LoanDTO dto = loanService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENTE')")
    @PostMapping
    public ResponseEntity<LoanDTO> create(@RequestBody LoanDTO dto) {
        LoanDTO created = loanService.create(dto);
        return ResponseEntity.created(URI.create("/loans/" + created.getId())).body(created);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENTE')")
    @PutMapping("/{id}/return")
    public ResponseEntity<Void> returnBook(@PathVariable Long id) {
        loanService.returnBook(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

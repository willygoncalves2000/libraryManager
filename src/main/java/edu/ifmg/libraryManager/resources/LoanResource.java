package edu.ifmg.libraryManager.resources;

import edu.ifmg.libraryManager.dtos.LoanDTO;
import edu.ifmg.libraryManager.entities.User;
import edu.ifmg.libraryManager.repository.UserRepository;
import edu.ifmg.libraryManager.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/loans")
public class LoanResource {

    @Autowired
    private LoanService loanService;
    @Autowired
    private UserRepository userRepository;


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping
    public ResponseEntity<List<LoanDTO>> findAll() {
        List<LoanDTO> list = loanService.findAll();
        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> findById(@PathVariable Long id) {
        LoanDTO dto = loanService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<LoanDTO> create(@RequestBody LoanDTO dto, Authentication authentication) {
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
        String email = jwtAuth.getToken().getClaim("username");


        User userLogged = userRepository.findByEmail(email);

        boolean isClient = userLogged.hasRole("ROLE_CLIENT");
        boolean isAdmin = userLogged.hasRole("ROLE_ADMIN");

        System.out.println("Usuário logado: " + userLogged.getEmail() + " (ID: " + userLogged.getId() + ")");
        System.out.println("DTO UserId: " + dto.getUserId());
        System.out.println("Usuário tem role ADMIN? " + userLogged.hasRole("ROLE_ADMIN"));
        System.out.println("Usuário tem role CLIENT? " + userLogged.hasRole("ROLE_CLIENT"));
        // Se for CLIENT e NÃO for ADMIN, restringe
        if (isClient && !isAdmin && !Objects.equals(dto.getUserId(), userLogged.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cliente só pode fazer empréstimo para si mesmo");
        }


        LoanDTO created = loanService.create(dto);
        return ResponseEntity.created(URI.create("/loans/" + created.getId())).body(created);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
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

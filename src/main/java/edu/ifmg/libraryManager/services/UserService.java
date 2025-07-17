package edu.ifmg.libraryManager.services;

import edu.ifmg.libraryManager.dtos.*;
import edu.ifmg.libraryManager.entities.Role;
import edu.ifmg.libraryManager.entities.Stay;
import edu.ifmg.libraryManager.entities.User;
import edu.ifmg.libraryManager.projections.UserDetailsProjection;
import edu.ifmg.libraryManager.repository.RoleRepository;
import edu.ifmg.libraryManager.repository.StayRepository;
import edu.ifmg.libraryManager.repository.UserRepository;
import edu.ifmg.libraryManager.services.exceptions.DatabaseException;
import edu.ifmg.libraryManager.services.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StayRepository stayRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> list = userRepository.findAll();
        return list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        System.out.println("entity password: " + passwordEncoder.encode("1234"));
        User saved = userRepository.save(entity);
        return new UserDTO(saved);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        try {
            User entity = userRepository.getReferenceById(id); // Verifica se existe
            copyDtoToEntity(dto, entity);                    // Traz os novos dados
            entity = userRepository.save(entity);
            return new UserDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound("User not found with id " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        if(!userRepository.existsById(id)) {
            throw new ResourceNotFound("User not found with id " + id);
        }
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.getRoles().clear();
        for (RoleDTO role : dto.getRoles()) {
            Role r = roleRepository.getReferenceById(role.getId());
            entity.getRoles().add(r);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = userRepository.searchUserAndRolesByEmail(username);
        if (result.size() == 0) {
            throw new UsernameNotFoundException("Email not found");
        }
        User user = new User();
        user.setEmail(result.get(0).getUsername());
        user.setPassword(result.get(0).getPassword());
        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }
        return user;
    }

    // Generates the invoice for a customer
    public InvoiceDTO generateInvoice(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(ResourceNotFound::new);
        System.out.println("user id: " + user.getId());

        if (isNullOrEmpty(user.getName()) || isNullOrEmpty(user.getEmail()) || isNullOrEmpty(user.getPhone())) {
            throw new ResourceNotFound("User must have name, email and phone to generate invoice");
        }

        List<Stay> stays = stayRepository.findByUserId(userId);
        if (stays.isEmpty()) {
            throw new ResourceNotFound("User with id " + userId + " does not have stays");
        }

        List<StayInvoiceDTO> validStays = stays.stream()
                .filter(s -> s.getRoom() != null
                    && s.getRoom().getDescription() != null
                    && s.getRoom().getPrice() != null)
                .map(s -> new StayInvoiceDTO(
                        s.getCheckInDate(),
                        s.getRoom().getDescription(),
                        s.getRoom().getPrice()))
                .collect(Collectors.toList());
        if (validStays.isEmpty()) {
            throw new ResourceNotFound("No valid stays found for user with id " + userId);
        }

        BigDecimal total = validStays.stream()
                .map(StayInvoiceDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println(user);
        System.out.println(validStays);
        System.out.println(total);

        return new InvoiceDTO(
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                validStays,
                total
        );


    }

    // *=*=*=*=*= Finds a customer's most expensive stay =*=*=*=*=*
    public StayInvoiceDTO findMostExpensiveStay(Long userId) {
        List<Stay> stays = getValidStays(userId);
        return stays.stream()
                .max(Comparator.comparing(s-> s.getRoom().getPrice()))
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFound("No valid stays found for user with id " + userId));
    }

    // *=*=*=*=*= Finds a customer's cheapest stay =*=*=*=*=*
    public StayInvoiceDTO findCheapestStay(Long userId) {
        List<Stay> stays = getValidStays(userId);
        return stays.stream()
                .min(Comparator.comparing(s-> s.getRoom().getPrice()))
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFound("No valid stays found for user with id " + userId));
    }

    // *=*=*=*=*= Finds the total value of a customer's stay =*=*=*=*=*
    public StayTotalDTO calculateTotalStayAmount(Long userId) {
        List<Stay> stays = getValidStays(userId);

        BigDecimal total = stays.stream()
                .map(s-> s.getRoom().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new StayTotalDTO(userId, total);
    }


    // *=*=*=*=*= Get a customer's stay list =*=*=*=*=*
    public List<Stay> getValidStays(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(ResourceNotFound::new);
        List<Stay> stays = stayRepository.findByUserId(userId);
        if (stays.isEmpty()) {
            throw new ResourceNotFound("User with id " + userId + " does not have stays");
        }
        return stays.stream()
                .filter(s -> s.getRoom() != null
                        && s.getRoom().getDescription() != null
                        && s.getRoom().getPrice() != null)
                .toList();
    }

    private StayInvoiceDTO toDTO(Stay stay) {
        return new StayInvoiceDTO(
                stay.getCheckInDate(),
                stay.getRoom().getDescription(),
                stay.getRoom().getPrice()
        );
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

}

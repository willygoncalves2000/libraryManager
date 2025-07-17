package edu.ifmg.libraryManager.services;

import edu.ifmg.libraryManager.dtos.StayInvoiceDTO;
import edu.ifmg.libraryManager.dtos.StayTotalDTO;
import edu.ifmg.libraryManager.entities.Room;
import edu.ifmg.libraryManager.entities.Stay;
import edu.ifmg.libraryManager.entities.User;

import edu.ifmg.libraryManager.repository.StayRepository;
import edu.ifmg.libraryManager.repository.UserRepository;
import edu.ifmg.libraryManager.services.exceptions.ResourceNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private StayRepository stayRepository;

    @InjectMocks
    private UserService userService;

    private User mockUser;
    private Stay stayCheap;
    private Stay stayExpensive;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);

        Room roomCheap = new Room();
        roomCheap.setDescription("Standard");
        roomCheap.setPrice(new BigDecimal("100.00"));

        Room roomExpensive = new Room();
        roomExpensive.setDescription("Suite");
        roomExpensive.setPrice(new BigDecimal("300.00"));

        stayCheap = new Stay();
        stayCheap.setId(1L);
        stayCheap.setRoom(roomCheap);
        stayCheap.setUser(mockUser);

        stayExpensive = new Stay();
        stayExpensive.setId(2L);
        stayExpensive.setRoom(roomExpensive);
        stayExpensive.setUser(mockUser);
    }

    @Test
    public void testFindMostExpensiveStay() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(stayRepository.findByUserId(1L)).thenReturn(List.of(stayCheap, stayExpensive));

        StayInvoiceDTO result = userService.findMostExpensiveStay(1L);

        assertEquals("Suite", result.getRoomDescription());
        assertEquals(new BigDecimal("300.00"), result.getAmount());
    }

    @Test
    public void testFindCheapestStay() {
        // simula o comportamento do metodo userRepository.findById
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        // Simula o retorno do metodo stayRepository.findByUserId.
        when(stayRepository.findByUserId(1L)).thenReturn(List.of(stayCheap, stayExpensive));

        // chama o metodo que quero testar
        StayInvoiceDTO result = userService.findCheapestStay(1L);

        // Verifica se o campo roomDescription do DTO retornado é "Standard", que era a descrição da stayCheap.
        assertEquals("Standard", result.getRoomDescription());
        // Verifica se o campo amount (preço do quarto da estadia mais barata) é 100.00, como esperado
        assertEquals(new BigDecimal("100.00"), result.getAmount());
    }

    @Test
    public void testCalculateTotalStayAmount() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(stayRepository.findByUserId(1L)).thenReturn(List.of(stayCheap, stayExpensive));
        StayTotalDTO result = userService.calculateTotalStayAmount(1L);

        assertEquals(1L, result.getUserId());
        assertEquals(new BigDecimal("400.00"), result.getTotalAmount());
    }

    @Test
    public void testGetValidStays_UserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> userService.getValidStays(99L));
    }

    @Test
    public void testGetValidStays_NoStays() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(stayRepository.findByUserId(1L)).thenReturn(List.of());

        assertThrows(ResourceNotFound.class, () -> userService.getValidStays(1L));
    }
}

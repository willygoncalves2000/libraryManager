package edu.ifmg.libraryManager.services;

import edu.ifmg.libraryManager.services.exceptions.ResourceNotFound;
import edu.ifmg.libraryManager.util.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    private long existingRoomId;
    private long nonExistingRoomId;

    @BeforeEach
    void setUp() {
        existingRoomId = 1L;
        nonExistingRoomId = 500L;
        Room room = Factory.createRoom();
    }

    @Test
    @DisplayName("Verificando se o objeto foi deletado no BD")
    void deleteShouldDoNothingWhenIdExists() {
        when(roomRepository.existsById(existingRoomId)).thenReturn(true);
        doNothing().when(roomRepository).deleteById(existingRoomId);
        Assertions.assertDoesNotThrow(() -> roomService.delete(existingRoomId));
        verify(roomRepository, times(1)).deleteById(existingRoomId);
    }

    @Test
    @DisplayName("Verificando se lança exceção se o objeto não existe no BD")
    void deleteShouldThrowExceptionWhenIdNonExists() {
        when(roomRepository.existsById(nonExistingRoomId)).thenReturn(false);
        //doNothing().when(roomRepository).deleteById(existingRoomId);
        Assertions.assertThrows(ResourceNotFound.class, () -> roomService.delete(nonExistingRoomId));
        verify(roomRepository, times(0)).deleteById(nonExistingRoomId);
    }
}
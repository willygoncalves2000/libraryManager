package edu.ifmg.libraryManager.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    @DisplayName("Verificando se o objeto não existe no BD depois de ser deletado.")
    public void deleteShoulDeleteObjectWhenIdExists() {
        roomRepository.deleteById(1L);
        Optional<Room> optional = roomRepository.findById(1L);
        Assertions.assertFalse(optional.isPresent());
    }
}

package edu.ifmg.libraryManager.services;

import edu.ifmg.libraryManager.dtos.StayDTO;
import edu.ifmg.libraryManager.entities.User;
import edu.ifmg.libraryManager.entities.Room;
import edu.ifmg.libraryManager.entities.Stay;
import edu.ifmg.libraryManager.repository.UserRepository;
import edu.ifmg.libraryManager.repository.RoomRepository;
import edu.ifmg.libraryManager.repository.StayRepository;
import edu.ifmg.libraryManager.services.exceptions.DatabaseException;
import edu.ifmg.libraryManager.services.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StayService {

    @Autowired
    private StayRepository stayRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;


    @Transactional(readOnly = true)
    public List<StayDTO> findAll() {
        List<Stay> stays = stayRepository.findAll();
        return stays.stream().map(stay -> new StayDTO(stay)).collect(Collectors.toList());
    }

    @Transactional
    public StayDTO insert(StayDTO dto) {
        Stay stay = new Stay();
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado"));
        stay.setUser(user);
        stay.setRoom(room);
        stay.setCheckInDate(dto.getCheckInDate());
        stay.setCheckOutDate(dto.getCheckOutDate());
        stay = stayRepository.save(stay);
        return new StayDTO(stay);
    }

    @Transactional
    public StayDTO update(Long id, StayDTO dto) {
        try {
            Stay entity = stayRepository.getReferenceById(id);

            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFound("Client not found with id: " + dto.getUserId()));

            Room room = roomRepository.findById(dto.getRoomId())
                    .orElseThrow(() -> new ResourceNotFound("Room not found with id: " + dto.getRoomId()));

            entity.setUser(user);
            entity.setRoom(room);
            entity.setCheckInDate(dto.getCheckInDate());
            entity.setCheckOutDate(dto.getCheckOutDate());

            entity = stayRepository.save(entity);
            return new StayDTO(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound("Stay not found with id: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        if(!stayRepository.existsById(id)) {
            throw new ResourceNotFound("Stay not found with id: " + id);
        }
        try {
            stayRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }



}

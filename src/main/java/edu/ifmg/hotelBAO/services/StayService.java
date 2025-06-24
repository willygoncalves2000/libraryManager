package edu.ifmg.hotelBAO.services;

import edu.ifmg.hotelBAO.dtos.ClientDTO;
import edu.ifmg.hotelBAO.dtos.StayDTO;
import edu.ifmg.hotelBAO.entities.Client;
import edu.ifmg.hotelBAO.entities.Room;
import edu.ifmg.hotelBAO.entities.Stay;
import edu.ifmg.hotelBAO.repository.ClientRepository;
import edu.ifmg.hotelBAO.repository.RoomRepository;
import edu.ifmg.hotelBAO.repository.StayRepository;
import edu.ifmg.hotelBAO.resources.ClientResource;
import edu.ifmg.hotelBAO.services.exceptions.DatabaseException;
import edu.ifmg.hotelBAO.services.exceptions.ResourceNotFound;
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
    private ClientRepository clientRepository;

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
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado"));
        stay.setClient(client);
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

            Client client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new ResourceNotFound("Client not found with id: " + dto.getClientId()));

            Room room = roomRepository.findById(dto.getRoomId())
                    .orElseThrow(() -> new ResourceNotFound("Room not found with id: " + dto.getRoomId()));

            entity.setClient(client);
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

package edu.ifmg.hotelBAO.services;

import edu.ifmg.hotelBAO.dtos.RoomDTO;
import edu.ifmg.hotelBAO.entities.Room;
import edu.ifmg.hotelBAO.repository.RoomRepository;
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
public class RoomService {

    @Autowired
    private RoomRepository repository;

    @Transactional(readOnly = true)
    public List<RoomDTO> findAll() {
        List<Room> list = repository.findAll();
        return list.stream().map(x -> new RoomDTO(x)).collect(Collectors.toList());
    }

    @Transactional
    public RoomDTO insert(RoomDTO dto) {
        Room entity = new Room();
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImageUrl(dto.getImageUrl());
        entity = repository.save(entity);
        return new RoomDTO(entity);
    }

    @Transactional
    public RoomDTO update(Long id, RoomDTO dto) {
        try {
            Room entity = repository.getReferenceById(id);
            entity.setDescription(dto.getDescription());
            entity.setPrice(dto.getPrice());
            entity.setImageUrl(dto.getImageUrl());
            entity = repository.save(entity);
            return new RoomDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound("Room not found with id: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        if(!repository.existsById(id)) {
            throw new ResourceNotFound("Room not found with id: " + id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }




}

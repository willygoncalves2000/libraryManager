package edu.ifmg.hotelBAO.services;

import edu.ifmg.hotelBAO.dtos.RoomDTO;
import edu.ifmg.hotelBAO.entities.Room;
import edu.ifmg.hotelBAO.repository.RoomRepository;
import edu.ifmg.hotelBAO.resources.RoomResource;
import edu.ifmg.hotelBAO.services.exceptions.DatabaseException;
import edu.ifmg.hotelBAO.services.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class RoomService {

    @Autowired
    private RoomRepository repository;

    @Transactional(readOnly = true)
    public Page<RoomDTO> findAll(Pageable pageable) {
        Page<Room> rooms = repository.findAll(pageable);
        return rooms.map(room -> new RoomDTO(room)
                        .add( linkTo( methodOn(RoomResource.class).findById(room.getId())).withSelfRel())
                        );
        //return list.stream().map(x -> new RoomDTO(x)).collect(Collectors.toList());
    }

    public RoomDTO findById(Long id) {
        Optional<Room> room = repository.findById(id);
        Room roomEntity = room.orElseThrow(() -> new ResourceNotFound("Room not found with id " + id));
        return new RoomDTO(roomEntity)
                .add( linkTo( methodOn(RoomResource.class).findById(roomEntity.getId())).withSelfRel())
                .add( linkTo(methodOn(RoomResource.class).update(roomEntity.getId(), null)).withRel("Update product"))
                .add( linkTo(methodOn(RoomResource.class).delete(roomEntity.getId())).withRel("Delete product"));
    }

    @Transactional
    public RoomDTO insert(RoomDTO dto) {
        Room entity = new Room();
        entity.setDescription(dto.getDescription());
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setImageUrl(dto.getImageUrl());
        entity = repository.save(entity);
        return new RoomDTO(entity)
                .add(linkTo(methodOn(RoomResource.class).delete(entity.getId())).withRel("Delete this product"))
                .add(linkTo(methodOn(RoomResource.class).update(entity.getId(), dto)).withRel("Update this product"))
                ;
    }

    @Transactional
    public RoomDTO update(Long id, RoomDTO dto) {
        try {
            Room entity = repository.getReferenceById(id);
            entity.setDescription(dto.getDescription());
            entity.setName(dto.getName());
            entity.setPrice(dto.getPrice());
            entity.setImageUrl(dto.getImageUrl());
            entity = repository.save(entity);
            return new RoomDTO(entity)
                    .add(linkTo(methodOn(RoomResource.class).delete(entity.getId())).withRel("Delete this product"))
                    ;
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

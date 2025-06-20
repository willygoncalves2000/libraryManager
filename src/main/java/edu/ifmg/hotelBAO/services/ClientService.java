package edu.ifmg.hotelBAO.services;

import edu.ifmg.hotelBAO.dtos.ClientDTO;
import edu.ifmg.hotelBAO.dtos.ClientInsertDTO;
import edu.ifmg.hotelBAO.entities.Client;
import edu.ifmg.hotelBAO.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<ClientDTO> findAll() {
        List<Client> list = repository.findAll();
        return list.stream().map(x -> new ClientDTO(x)).collect(Collectors.toList());
    }

    @Transactional
    public ClientDTO insert(ClientInsertDTO dto) {
        Client entity = new Client();
        copyInsertDtoToEntity(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        Client saved = repository.save(entity);
        return new ClientDTO(saved);
    }

    private void copyInsertDtoToEntity(ClientInsertDTO dto, Client entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setLogin(dto.getLogin());
    }

}

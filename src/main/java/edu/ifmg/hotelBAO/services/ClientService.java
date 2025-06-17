package edu.ifmg.hotelBAO.services;

import edu.ifmg.hotelBAO.dtos.ClientDTO;
import edu.ifmg.hotelBAO.entities.Client;
import edu.ifmg.hotelBAO.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public List<ClientDTO> findAll() {
        List<Client> list = repository.findAll();
        return list.stream().map(x -> new ClientDTO(x)).collect(Collectors.toList());
    }

}

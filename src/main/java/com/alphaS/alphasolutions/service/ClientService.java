package com.alphaS.alphasolutions.service;

import com.alphaS.alphasolutions.model.ClientModel;
import com.alphaS.alphasolutions.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    public ClientService(ClientRepository repository) {
        this.clientRepository = repository;
    }

    public int createClient(String clientName, int contactPoNo, String contactPerson, int companyPoNo, String address, int zipCode, String country) throws SQLException {
       return clientRepository.createClient(clientName, contactPoNo, contactPerson, companyPoNo, address, zipCode, country);
    }

    public List<ClientModel> readClients() throws SQLException {
        return clientRepository.readClients();
    }
    public List<ClientModel> readSpecificClient(int clientId) throws SQLException {
        return (List<ClientModel>) clientRepository.readSpecificClient(clientId);
    }

    public String editClient(String clientName, int contactPoNo, String contactPerson, int companyPoNo, String address, int zipCode, String country, int clientId) throws SQLException {
       return clientRepository.editClient(clientName, contactPoNo, contactPerson, companyPoNo, address, zipCode, country, clientId);

    }

    public List<ClientModel> searchClient(String search) throws SQLException {
       return clientRepository.searchClient(search);

    }

    public boolean deleteClient(int clientId) {
        return clientRepository.deleteClient(clientId);
    }
}

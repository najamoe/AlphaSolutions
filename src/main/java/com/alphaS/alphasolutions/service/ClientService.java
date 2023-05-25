package com.alphaS.alphasolutions.service;

import com.alphaS.alphasolutions.model.ClientModel;
import com.alphaS.alphasolutions.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public int createClient(String clientName, int contactPoNo, String contactPerson, int companyPoNo, String address, int zipCode, String country) throws SQLException {
        return clientRepository.createClient(clientName, contactPoNo, contactPerson, companyPoNo, address, zipCode, country);
    }

    public List<ClientModel> readClients() throws SQLException {
        return clientRepository.readClients();
    }
    public ClientModel readSpecificClient(int clientId) {
        return clientRepository.readSpecificClient(clientId);
    }

    public String editClient(String clientName, int contactPoNo, String contactPerson, int companyPoNo, String address, int zipCode, String country, int clientId) throws SQLException {
        return clientRepository.editClient(clientName, contactPoNo, contactPerson, companyPoNo, address, zipCode, country, clientId);
    }

    }

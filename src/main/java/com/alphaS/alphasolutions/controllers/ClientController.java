package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.repositories.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Controller
public class ClientController {
    private final ClientRepository clientRepository;


    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    @PostMapping("/clients/create")
    public ResponseEntity<String> createClient(@RequestParam String clientName,
                                               @RequestParam String contactPoNo,
                                               @RequestParam String contactPerson,
                                               @RequestParam String companyPoNo,
                                               @RequestParam String address,
                                               @RequestParam String zipCode,
                                               @RequestParam String country,
                                               @RequestParam String clientId) {
        try {
            String result = clientRepository.createClient(clientName, contactPoNo, contactPerson, companyPoNo, address, zipCode, country, clientId);
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create client");
        }
    }

    @GetMapping("/clients/search")
    @ResponseBody
    public ResponseEntity<String> searchClient(@RequestParam String clientName) {
        try {
            String result = clientRepository.searchClients(clientName).toString();
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to search client");
        }
    }

    @PostMapping("/clients/edit")
    public ResponseEntity<String> editClient(@RequestParam String clientName,
                                             @RequestParam String contactPoNo,
                                             @RequestParam String contactPerson,
                                             @RequestParam String companyPoNo,
                                             @RequestParam String address,
                                             @RequestParam String zipCode,
                                             @RequestParam String country,
                                             @RequestParam String clientId) {
        try {
            String result = clientRepository.editClient(clientName, contactPoNo, contactPerson, companyPoNo, address, zipCode, country, clientId);
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit client");
        }
    }
    @PostMapping("/clients/delete")
    public ResponseEntity<String> deleteClient(@RequestParam int clientID){
        boolean deletionStatus = clientRepository.deleteClient(clientID);
        if (deletionStatus) {
            return new ResponseEntity<>("Client with ID " + clientID + " was deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete client with ID " + clientID, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.ClientModel;
import com.alphaS.alphasolutions.model.ProjectModel;
import com.alphaS.alphasolutions.repositories.ClientRepository;
import com.alphaS.alphasolutions.repositories.ProjectRepository;
import com.alphaS.alphasolutions.service.ClientService;
import com.alphaS.alphasolutions.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;

@Controller
public class ClientController {

    private final ProjectService projectService;
    private final ClientService clientService;
    private final ClientRepository clientRepository;
    private final ProjectRepository projectRepository;

    public ClientController(ClientService clientService, ProjectService projectService,
                            ClientRepository clientRepository, ProjectRepository projectRepository) {
        this.clientService = clientService;
        this.projectService = projectService;
        this.clientRepository = clientRepository;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/createclient")
    public String clients(Model model, HttpSession session, @ModelAttribute("projectId") Integer projectId) {
        if (projectId == null) {
            // Handle the case when projectId is null
            // For example, you can return an error page or redirect to a different URL
            return "redirect:/projecterror";
        }

        model.addAttribute("client", new ClientModel());
        model.addAttribute("projectId", projectId);
        return "createClient";
    }

    @PostMapping("/createclient/{projectId}")
    public String addClientToProject(
            @PathVariable("projectId") int projectId,
            @ModelAttribute("client") ClientModel client,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) throws SQLException {
        // Retrieve the username and password from the session
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        // Create the client using the repository
        int clientId = clientRepository.createClient(
                client.getClientName(), client.getContactPoNo(), client.getContactPerson(),
                client.getCompanyPoNo(), client.getAddress(), client.getZipcode(), client.getCountry()
        );
        
        String message = null;
        // Add the client to the project using the repository
        if (clientId > 0) {
            ProjectModel project = projectRepository.addClientToProject(projectId, clientId, username, password);
            if (project != null) {
                // Client added to the project successfully
                redirectAttributes.addFlashAttribute("successMessage", "Client added successfully.");
                return "redirect:/clientsuccess/" + projectId;
            } else {
                // Failed to add client to the project
                message = "Failed to add client to the project";
            }
        }

        model.addAttribute("message", message);
        return "clientsuccess";
    }

    @GetMapping("/clientsuccess/{projectId}")
    public String clientSuccess(@PathVariable("projectId") int projectId, Model model, HttpSession session) {
        // Add projectId to the model if needed
        model.addAttribute("projectId", projectId);
        return "clientSuccess";
    }


    @GetMapping("/readclients")
    public String readClients(Model model, HttpSession session) {
        try {
            List<ClientModel> clients = clientService.readClients();
            model.addAttribute("clients", clients);

            return "readclients";
        } catch (SQLException e) {
            String errorMessage = "Failed to retrieve projects from the database. Please try again later.";
            model.addAttribute("error", errorMessage);
            return "readclients";
        }
    }

   @PostMapping("/clients/edit")
    public ResponseEntity<String> editClient(@RequestParam String clientName,
                                             @RequestParam int contactPoNo,
                                             @RequestParam String contactPerson,
                                             @RequestParam int companyPoNo,
                                             @RequestParam String address,
                                             @RequestParam int zipCode,
                                             @RequestParam String country,
                                             @RequestParam int clientId) {
        try {
            String result = clientService.editClient(clientName, contactPoNo, contactPerson, companyPoNo, address, zipCode, country, clientId);
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit client");
        }
    }
    @PostMapping("/clients/delete")
    public ResponseEntity<String> deleteClient(@RequestParam int clientID){
        boolean deletionStatus = clientService.deleteClient(clientID);
        if (deletionStatus) {
            return new ResponseEntity<>("Client with ID " + clientID + " was deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete client with ID " + clientID, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

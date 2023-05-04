package com.alphaS.alphasolutions.repositories;

import ch.qos.logback.core.net.server.Client;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CreateClientTest {

    @Test
    void CreateClient() throws SQLException {
        String result = ProjectRepository.createClient("Test Client", "12345678", "John Doe", "98765432", "Byvej 69", "3400", "DK", "01");
        assertEquals("Client successfully added to database", result);
    }

    @Test
    void editClient() {
    }

    @Test
    void searchClients() {
    }

    @Test
    void deleteClient() {
    }
}
/*package com.alphaS.alphasolutions.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class ClientRepositoryTest {

    @InjectMocks
    private ClientRepository clientRepository;

    @Mock //Allows us to control the behavior of the dependencies and simulate the database interactions
    private DataSource dataSource;

    @BeforeEach
    public void setup() throws SQLException {
        MockitoAnnotations.openMocks(this);

        // Set up a mock database connection
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @Test
    void createClient() throws SQLException {

        // Arrange - create test data
        String clientName = "Client name";
        String contactPoNo = "11111111";
        String contactPerson = "Jane Smith";
        String companyPoNo = "22222222";
        String address = "Company address";
        String zipCode = "1111";
        String country = "Country";
        String clientId = "1";

        // Set up a mock PreparedStatement and ResultSet
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet generatedKeys = mock(ResultSet.class);

        // Set up the behavior for the mock Connection
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        when(stmt.getGeneratedKeys()).thenReturn(generatedKeys);
        when(generatedKeys.next()).thenReturn(true);
        when(generatedKeys.getInt(1)).thenReturn(1);

        // Call the createClient method
        String result = clientRepository.createClient(clientName, contactPoNo, contactPerson, companyPoNo, address, zipCode, country, clientId);

        // Assert the result
        assertEquals("Client successfully created", result);

        // Verify that the necessary methods were called
        verify(dataSource).getConnection();
        verify(connection).prepareStatement(anyString(), anyInt());
        verify(stmt).setString(eq(1), eq(clientName));
        verify(stmt).setString(eq(2), eq(contactPoNo));
        verify(stmt).setString(eq(3), eq(contactPerson));
        verify(stmt).setString(eq(4), eq(companyPoNo));
        verify(stmt).setString(eq(5), eq(address));
        verify(stmt).setString(eq(6), eq(zipCode));
        verify(stmt).setString(eq(7), eq(country));
        verify(stmt).setString(eq(8), eq(clientId));
        verify(stmt).executeUpdate();
        verify(stmt).getGeneratedKeys();
        verify(generatedKeys).next();
        verify(generatedKeys).getInt(1);
    }


    @Test
    void searchClient() {

    }
}


 */

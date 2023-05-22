package com.alphaS.alphasolutions.repositories;

import com.alphaS.alphasolutions.model.*;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientRepository {

    private final DataSource dataSource;

    public ClientRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int createClient(String clientName, int contactPoNo, String contactPerson, int companyPoNo, String address, int zipCode, String country) throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            String sql = "INSERT INTO taskcompass.client (client_name, contact_po_no, contact_person, company_po_no, address, zip_code, country) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, clientName);
            stmt.setInt(2, contactPoNo);
            stmt.setString(3, contactPerson);
            stmt.setInt(4, companyPoNo);
            stmt.setString(5, address);
            stmt.setInt(6, zipCode);
            stmt.setString(7, country);

            // Execute the query and get the number of rows inserted
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                // Retrieve the auto-generated clientId
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve auto-generated clientId");
                }
            } else {
                throw new SQLException("Something went wrong, no client added");
            }
        }
    }

    public List<ClientModel> readClients() throws SQLException {
        Connection con = dataSource.getConnection();
        String sql = "SELECT * FROM taskcompass.Client ";
        PreparedStatement stmt = con.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();
        List<ClientModel> clients = new ArrayList<>();
        while (rs.next()) {
            ClientModel client = new ClientModel();
            client.setClientName(rs.getString("client_name"));
            client.setContactPoNo(rs.getInt("contact_po_no"));
            client.setContactPerson(rs.getString("contact_person"));
            client.setCompanyPoNo(rs.getInt("company_po_no"));
            client.setAddress(rs.getString("address"));
            client.setZipcode(rs.getInt("zip_code"));
            client.setCountry(rs.getString("country"));
            client.setClientId(rs.getInt("client_id"));
            clients.add(client);
        }

        rs.close();
        stmt.close();
        con.close();

        return clients;
    }

    public ClientModel readSpecificClient(int clientId) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT * FROM taskcompass.Client WHERE client_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ClientModel client = new ClientModel();
                client.setClientName(rs.getString("client_name"));
                client.setContactPoNo(rs.getInt("contact_po_no"));
                client.setContactPerson(rs.getString("contact_person"));
                client.setCompanyPoNo(rs.getInt("company_po_no"));
                client.setAddress(rs.getString("address"));
                client.setZipcode(rs.getInt("zip_code"));
                client.setCountry(rs.getString("country"));
                client.setClientId(rs.getInt("client_id"));
                return client;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Return null if the client is not found
    }

    public String editClient(String newClientName, int newContactPoNo, String newContactPerson, int newCompanyPoNo,
                             String newAddress, int newZipCode, String newCountry, int clientId) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "UPDATE taskcompass.Client SET client_name=?, contact_po_no=?, contact_person=?, " +
                    "company_po_no=?, address=?, zip_code=?, country=? WHERE client_id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, newClientName);
            stmt.setInt(2, newContactPoNo);
            stmt.setString(3, newContactPerson);
            stmt.setInt(4, newCompanyPoNo);
            stmt.setString(5, newAddress);
            stmt.setInt(6, newZipCode);
            stmt.setString(7, newCountry);
            stmt.setInt(8, clientId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return "Client successfully updated with your changes";
            }
            return "Failed to edit client";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String deletedClient(int clientId) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "DELETE FROM taskcompass.Client WHERE client_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, clientId);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                return "Client deleted successfully";
            } else {
                return "Client with ID " + clientId + " does not exist";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

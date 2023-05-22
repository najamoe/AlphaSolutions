package com.alphaS.alphasolutions.repositories;

import com.alphaS.alphasolutions.model.ClientModel;
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



    //TODO: READ
    //TODO: Sage recommends us to base our methods by id's. will be easier to identify our clients (e.x EditProject method)
    public String editClient(String clientName, int contactPoNo, String contactPerson, int companyPoNo, String address, int zipCode, String country, int clientId) throws SQLException {
        Connection con = dataSource.getConnection();
        String sql = "UPDATE taskcompass.client SET client_name=?, contact_po_no=?, contact_person=?, company_po_no=?, address=?, zip_code=?, country=?, client_id=? WHERE client_id=?";
        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setString(1, clientName);
        stmt.setInt(2, contactPoNo);
        stmt.setString(3, contactPerson);
        stmt.setInt(4, companyPoNo);
        stmt.setString(5, address);
        stmt.setInt(6, zipCode);
        stmt.setString(7, country);
        stmt.setInt(8, clientId);

        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            return "Client successfully updated in database";
        } else {
            return "Something went wrong, no client updated";
        }
    }

    public List<ClientModel> searchClient(String search) throws SQLException {
        List<ClientModel> clients = new ArrayList<>();

        Connection con = dataSource.getConnection();
        String sql = "SELECT * FROM taskcompass.client WHERE client_name LIKE ? OR contact_person LIKE ? OR contact_po_no LIKE ? OR company_po_no LIKE ? OR address LIKE ? OR zip_code LIKE ? OR country LIKE ? OR client_id LIKE ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            // Set the search term as the parameter value for each placeholder
            String likeSearchTerm = "%" + search + "%";
            for (int i = 1; i <= 8; i++) {
                stmt.setString(i, likeSearchTerm);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ClientModel client = new ClientModel();
                    client.setClientId(rs.getInt("client_id"));
                    client.setClientName(rs.getString("client_name"));
                    client.setContactPerson(rs.getString("contact_person"));
                    client.setContactPoNo(rs.getInt("contact_po_no"));
                    client.setCompanyPoNo(rs.getInt("company_po_no"));
                    client.setAddress(rs.getString("address"));
                    client.setZipcode(rs.getInt("zip_code"));
                    client.setCountry(rs.getString("country"));
                    clients.add(client);
                }
            }
        }
        return clients;
    }

    public boolean deleteClient(int clientId) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "DELETE FROM taskcompass.client WHERE client_id =?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, clientId);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

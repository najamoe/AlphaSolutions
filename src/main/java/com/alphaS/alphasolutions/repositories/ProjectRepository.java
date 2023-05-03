package com.alphaS.alphasolutions.repositories;

import com.alphaS.alphasolutions.model.ClientModel;
import com.alphaS.alphasolutions.model.ProjectModel;
import com.alphaS.alphasolutions.model.TaskModel;
import com.alphaS.alphasolutions.model.TeamModel;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository {


    private final DataSource dataSource;

    public ProjectRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //Create Client
    public String createClient(String clientName, String contactPoNo, String contactPerson, String companyPoNo, String address, String zipCode, String country, String clientId) throws SQLException {
        Connection con = dataSource.getConnection();
        String sql = "INSERT INTO taskcompass.client (client_name, contact_po_no, contact_person, company_po_no, address, zip_code, country, client_id) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setString(1, clientName);
        stmt.setString(2, contactPoNo);
        stmt.setString(3, contactPerson);
        stmt.setString(4, companyPoNo);
        stmt.setString(5, address);
        stmt.setString(6, zipCode);
        stmt.setString(7, country);
        stmt.setString(8, clientId);

        // Execute the query and get the result set
        int rowsInserted = stmt.executeUpdate();

        if (rowsInserted > 0) {
            return "Client successfully added to database";
        } else {
            return "Something went wrong, no client added";
        }
    }

    //Edit Client
    public String editClient(String clientName, String contactPoNo, String contactPerson, String companyPoNo, String address, String zipCode, String country, String clientId) throws SQLException {
        Connection con = dataSource.getConnection();
        String sql = "UPDATE taskcompass.client SET client_name=?, contact_po_no=?, contact_person=?, company_po_no=?, address=?, zip_code=?, country=?, client_id=? WHERE client_id=?";
        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setString(1, clientName);
        stmt.setString(2, contactPoNo);
        stmt.setString(3, contactPerson);
        stmt.setString(4, companyPoNo);
        stmt.setString(5, address);
        stmt.setString(6, zipCode);
        stmt.setString(7, country);
        stmt.setString(8, clientId);
        stmt.setString(9, clientId);

        // Execute the query and get the result set
        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            return "Client successfully updated in database";
        } else {
            return "Something went wrong, no client updated";
        }
    }

        //Search clients
        public List<ClientModel> searchClients(String search) throws SQLException {
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
                     //   client.add(client); //TODO FIX ADD
                    }
                }
            }
            return clients;
        }

    //Delete client
    public String deleteClient(int clientId) {
        String message;
        try (Connection con = dataSource.getConnection()){
            String sql = "DELETE FROM taskcompass.client WHERE client_id =?";
            PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, clientId);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                message = "Client with ID " + clientId + " has been deleted successfully";
            } else {
                message = "Client with ID " + clientId + " does not exist";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return message;
    }

        //Search project
        public List<ProjectModel> searchProjects(String search) throws SQLException {
            List<ProjectModel> projects = new ArrayList<>();

            Connection con = dataSource.getConnection();
            String sql = "SELECT p.* FROM taskcompass.project p " +
                    "INNER JOIN taskcompass.client c ON p.client_id = c.client_id " +
                    "WHERE p.project_name LIKE ? OR p.project_id LIKE ? OR c.clientName LIKE ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, "%" + search + "%");
                stmt.setString(2, "%" + search + "%");
                stmt.setString(3, "%" + search + "%");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    // Map the result set to your ProjectModel object and add it to the list
                    ProjectModel project = new ProjectModel();
                    project.setProjectId(rs.getInt("project_id"));
                    project.setProjectName(rs.getString("project_name"));
                    // Set other fields as needed
               //     project.add(project);    //TODO FIX ADD
                }
            }
            return projects;
        }


    //Delete project
    public String deleteProject(int projectID) {
        String message;

        try (Connection con = dataSource.getConnection()){
            String sql = "DELETE FROM taskcompass.project WHERE project_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, projectID);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                message = "Project with ID " + projectID + " has been deleted successfully";
            } else {
                message = "Project with ID " + projectID + " does not exist";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return message;
    }



    //Method for creating a team
    public int createTeam(String teamName) throws SQLException {
        Connection con = dataSource.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Create a new team
            String sql = "INSERT INTO team (name) VALUES (?)";
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, teamName);
            stmt.executeUpdate();

            // Get the ID of the newly created team
            int teamId;
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                teamId = rs.getInt(1);
            } else {
                throw new SQLException("Creating team failed, no ID obtained.");
            }

            con.commit();

            return teamId;

        } catch (SQLException ex) {
            if (con != null) {
                con.rollback();
            }
            throw ex;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            con.close();
        }
    }

    //Method for adding a member to a team
    public void addTeamMembers(int teamId, List<String> userNames) throws SQLException {
        Connection con = dataSource.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            for (String userName : userNames) {
                String[] nameParts = userName.split("\\s+");
                String firstName = nameParts[0];
                String lastName = nameParts[1];

                // Find the user ID by first and last name
                String sql = "SELECT * FROM user WHERE first_name = ? AND last_name = ?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                rs = stmt.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("id");
                    // Check if the user is already in another team
                    sql = "SELECT COUNT(*) AS team_count FROM user_team WHERE user_id = ?";
                    stmt = con.prepareStatement(sql);
                    stmt.setInt(1, userId);
                    rs = stmt.executeQuery();

                    int teamCount = rs.getInt("team_count");
                    if (teamCount > 0) {
                        // Update the user's team if they are already in one
                        sql = "UPDATE user_team SET team_id = ? WHERE user_id = ?";
                        stmt = con.prepareStatement(sql);
                        stmt.setInt(1, teamId);
                        stmt.setInt(2, userId);
                        stmt.executeUpdate();
                    } else {
                        // Add the user to the team
                        sql = "INSERT INTO user_team (user_id, team_id) VALUES (?, ?)";
                        stmt = con.prepareStatement(sql);
                        stmt.setInt(1, userId);
                        stmt.setInt(2, teamId);
                        stmt.executeUpdate();
                    }
                } else {
                    throw new SQLException("User with name " + userName + " not found.");
                }
            }

            con.commit();

        } catch (SQLException ex) {
            if (con != null) {
                con.rollback();
            }
            throw ex;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            con.close();
        }

        /*

        //Method for creating new project
        public ProjectModel createProject () {

        }

        //Method for creating new tasks
        public TaskModel createTask () {

        }
        public ClientModel createClient () {

        }
        public ClientModel searchClient () {

        }

        public TeamModel createTeam () {

        }

        //Method for searching projects
        public ProjectModel searchProject () {

        }

        //Method for editing projects
        public ProjectModel editProject () {

        }
        //Method for deleting projects
        public ProjectModel deleteProject () {

        }

*/
    }
}

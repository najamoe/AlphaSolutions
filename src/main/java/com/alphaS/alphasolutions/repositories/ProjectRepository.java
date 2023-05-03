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






    //Remove member from a team
    public String removeMemberFromTeam(int teamId, int userId) throws SQLException {
        String message;

        try (Connection con = dataSource.getConnection()){
            String sql = "DELETE FROM taskcompass.Team_users WHERE team_id = ? AND user_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, teamId);
            preparedStatement.setInt(2,userId);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                message = userId + " has been removed from the " + teamId + " team, successfully";
            } else {
                message = userId + " is not a member of the " + teamId + " team";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return message;

    }

    public String editTeamName(String teamName) throws SQLException {
        Connection con = dataSource.getConnection();String sql = "UPDATE taskcompass.Team SET team_name = ? WHERE team_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setString(1, teamName);
        // Execute the query and get the result set
        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            return "Client successfully updated in database";
        } else {
            return "Something went wrong, no client updated";
        }
    }



    //Method for creating a team
    public int createTeam(String teamName) throws SQLException {
        try (Connection con = dataSource.getConnection();
             Statement stmt = con.createStatement()) {

            // Create a new team
            String sql = "INSERT INTO taskcompass.Team (name) VALUES ('" + teamName + "')";
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            // Get the ID of the newly created team
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Creating team failed, no ID obtained.");
                }
            }
        }
    }


    //Method for adding a member to a team
    public void addTeamMembers(int teamId, List<String> userNames) throws SQLException {
        String sql = "INSERT INTO taskcompass.user_team (user_id, team_id) SELECT u.id, t.id FROM taskcompass.user u  JOIN taskcompass.team t ON t.team_name = ?  WHERE u.first_name = ? AND u.last_name = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            for (String userName : userNames) {
                String[] nameParts = userName.split("\\s+");
                String firstName = nameParts[0];
                String lastName = nameParts[1];
                stmt.setInt(1, teamId);
                stmt.setString(2, firstName);
                stmt.setString(3, lastName);
                int rowCount = stmt.executeUpdate();
                if (rowCount == 0) {
                    // Log error and continue with next user
                    System.err.println("User with name " + userName + " not found.");
                }
            }
            con.commit();
        } catch (SQLException ex) {
            // Log error and rollback transaction
            System.err.println("Error adding team members: " + ex.getMessage());
            throw ex;
        }
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


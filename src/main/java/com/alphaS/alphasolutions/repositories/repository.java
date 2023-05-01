package com.alphaS.alphasolutions.repositories;

import com.alphaS.alphasolutions.model.*;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class repository {

  private final DataSource dataSource;

  public repository(DataSource dataSource) {
    this.dataSource = dataSource;
  }
  //Method for signin in
  public UserModel signin(String username, String password) throws SQLException {
      Connection con = dataSource.getConnection();
      String sql = "SELECT * FROM taskcompass.user WHERE username=? AND password=?";
      PreparedStatement stmt = con.prepareStatement(sql);
      stmt.setString(1, username);
      stmt.setString(2, password);

      // Execute the query and get the result set
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        UserModel user = new UserModel(rs.getString("username"), rs.getString("email"));
        return user;
      } else {
        // User authentication failed
        throw new SQLException("Username or password incorrect");
      }
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
  }


/*
    //Method for creating new project
    public ProjectModel createProject(){

    }

    //Method for creating new tasks
    public TaskModel createTask(){

    }
    public ClientModel createClient(){

    }
    public ClientModel searchClient(){

    }

    public TeamModel createTeam(){

    }

    //Method for searching projects
    public ProjectModel searchProject(){

    }

    //Method for editing projects
    public ProjectModel editProject(){

    }
    //Method for deleting projects
    public ProjectModel deleteProject(){

    }

*/

    }
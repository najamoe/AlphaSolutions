package com.alphaS.alphasolutions.repositories;

import com.alphaS.alphasolutions.model.*;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

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

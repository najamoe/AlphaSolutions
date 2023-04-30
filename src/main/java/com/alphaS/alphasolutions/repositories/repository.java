package com.alphaS.alphasolutions.repositories;

import com.alphaS.alphasolutions.model.*;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Repository
public class repository {

  String DBURL = System.getenv("DB_URL");
  String DBUSERNAME = System.getenv("DB_USERNAME");
  String DBPASSWORD = System.getenv("DB_PASSWORD");

    //Method for signin in
    public UserModel signin(String username, int password) throws SQLException {
        Connection con = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
    }
    //String sql = "SELECT u.email, wl.* \n" +
    //                    "FROM miniProjekt.user u \n" +
    //                    "JOIN miniProjekt.wishLists wl ON u.email = wl.userEmail \n" +
    //                    "WHERE u.email = ?";
    //            try (PreparedStatement statement = connection.prepareStatement(sql)) {
    //                statement.setString(1, email);
    //                try (ResultSet resultSet = statement.executeQuery()) {
    //                    if (resultSet.next()) {
    //                        User user = new User();
    //                        user.setEmail(resultSet.getString("email"));
    //                        return user;
    //                    } else {
    //                        throw new LoginSampleException("Could not validate user");
    //                    }
    //                }
    //            }
    //        } catch (SQLException e) {
    //            throw new RuntimeException("Error verifying user", e);
    //        }
    //    }
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

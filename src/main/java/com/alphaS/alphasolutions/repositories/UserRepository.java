package com.alphaS.alphasolutions.repositories;

import org.springframework.stereotype.Repository;
import com.alphaS.alphasolutions.model.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository

public class UserRepository {


        private final DataSource dataSource;

        public UserRepository(DataSource dataSource) {
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
    }


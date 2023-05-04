package com.alphaS.alphasolutions.repositories;

import org.springframework.stereotype.Repository;
import com.alphaS.alphasolutions.model.*;
import javax.sql.DataSource;
import java.sql.*;
@Repository
public class EmployeeRepository {

        private final DataSource dataSource;
        public EmployeeRepository(DataSource dataSource) {
            this.dataSource = dataSource;
        }
    public EmployeeModel logIn(String username, String password) throws SQLException {
        Connection con = dataSource.getConnection();
        String sql = "SELECT * FROM taskcompass.user WHERE username=? AND password=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new EmployeeModel(rs.getString("username"), rs.getString("email"));
        } else {
            throw new SQLException("Username or password incorrect");
        }
    }

}
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
        String sql = "SELECT * FROM taskcompass.employee WHERE username=? AND password=?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve employee details from the result set
                    int employeeId = rs.getInt("id");
                    String Username = rs.getString("username");
                    EmployeeModel employee = new EmployeeModel(employeeId, Username);

                    return employee;
                } else {
                    throw new SQLException("Could not validate user");
                }
            }
        } finally {
            con.close();
        }
    } }


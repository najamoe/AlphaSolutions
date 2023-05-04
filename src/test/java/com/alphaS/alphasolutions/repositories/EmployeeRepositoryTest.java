package com.alphaS.alphasolutions.repositories;

import com.alphaS.alphasolutions.model.EmployeeModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRepositoryTest {

    @Test
    void ValidlogIn() {
        EmployeeModel employee = new EmployeeModel();
        String username;
        String password;
        EmployeeModel result = employee.logIn(username, password);
        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    void InvalidlogIn() {
    }

}
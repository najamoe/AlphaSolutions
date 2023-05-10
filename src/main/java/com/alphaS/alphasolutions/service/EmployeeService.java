package com.alphaS.alphasolutions.service;

import com.alphaS.alphasolutions.model.EmployeeModel;
import com.alphaS.alphasolutions.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeModel logIn(String username, String password) throws SQLException {
        return employeeRepository.logIn(username, password);

    }
}

package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.EmployeeModel;
import com.alphaS.alphasolutions.repositories.EmployeeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.SQLException;

@Controller
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/signin")
    public String signIn(){
        return "index";
    }
    @PostMapping("/signin")
    public String signInPostMapping(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model){
        try {
            EmployeeModel employee = employeeRepository.logIn(username, password);
            if (employee != null) {
                session.setAttribute("user", employee); // store user in session
                return "redirect:/dashboard"; // redirect to dashboard page if login is successful
            } else {
                model.addAttribute("error", "Username or password incorrect"); // add error message to model
                return "signin"; // return sign-in page if login is unsuccessful
            }
        } catch (SQLException e) {
            model.addAttribute("error", "An error occurred during login"); // add error message to model
            return "signin"; // return sign-in page if an error occurs during login
        }
    }


    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        session.invalidate();
        return "redirect:/signin";
    }
}

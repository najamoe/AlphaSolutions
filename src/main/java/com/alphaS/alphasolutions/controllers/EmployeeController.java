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
    public String showLoginForm(Model model) {

        return "index";
    }

    @PostMapping("/signin")
    public String signInPostMapping(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model) {
        try {
            EmployeeModel employee = employeeRepository.logIn(username, password);
            if (employee == null) {
               model.addAttribute("error", "Username or password incorrect");
               return "index";
            } session.setAttribute("username", username);
            session.setAttribute("password", password);
            return "redirect:/dashboard";
        } catch (SQLException e) {
            model.addAttribute("error", "an error occured");
            return "index";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        if (session.getAttribute("username") == null) {
            return "redirect:/signin";
        }
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        session.invalidate();
        return "redirect:/signin";
    }
}

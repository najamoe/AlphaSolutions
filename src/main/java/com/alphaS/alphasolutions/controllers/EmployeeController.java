package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.EmployeeModel;
import com.alphaS.alphasolutions.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //user objet
    //mere ren
    @GetMapping("/signin")
    public String showLoginForm(Model model) {
        return "index";
    }

    @PostMapping("/signin")
    public String signInPostMapping(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model) {
        try {
            EmployeeModel employee = employeeService.logIn(username, password);
            if (employee == null) {
                model.addAttribute("error", "Username or password is incorrect");
                return "index";
            }
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            session.setAttribute("employeeId", employee.getEmployeeId()); // Add this line
            return "redirect:/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Username or password is incorrect");
            return "index";
        }
    }


    @GetMapping("/account")
    public String showAccount(Model model) {
        return "account";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        session.invalidate();
        return "redirect:/signin";
    }
}

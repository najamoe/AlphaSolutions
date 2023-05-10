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
     /*
        model.addAttribute("username", ""); // Set an empty initial value for the username
        model.addAttribute("password", ""); // Set an empty initial value for the password
         */
        return "index";
    }



    @PostMapping("/signin")
    public String signInPostMapping(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model) {
        try {
            EmployeeModel employee = employeeService.logIn(username, password);
            if (employee != null) {
                session.setAttribute("user", employee);
                return "redirect:/dashboard";
            } else {
                model.addAttribute("username", username);
                model.addAttribute("password", password);
                model.addAttribute("error", "Username or password incorrect");
                return "index";
            }
        } catch (SQLException e) {
            model.addAttribute("username", username);
            model.addAttribute("password", password);
            model.addAttribute("error", "An error occurred during login");
            return "index";
        } finally {
            System.out.println(username);
            System.out.println(password);
        }
    }


    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        session.invalidate();
        return "redirect:/signin";
    }
}

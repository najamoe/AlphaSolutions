package com.alphaS.alphasolutions.controllers;

import com.alphaS.alphasolutions.model.UserModel;
import com.alphaS.alphasolutions.repositories.repository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class TaskCompassController {

    //Handle dataaccess
    Repository repository;


    @GetMapping("")
    public String Index(){
        return "index";
    }

    @GetMapping("/signin")
    public String signin(){
        return "index";
    }

    @PostMapping("/signin")
    public String signinpostmapping(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model,@Autowired com.alphaS.alphasolutions.repositories.repository repository){
        try {
            UserModel usermodel = repository.signin(username, password);
            session.setAttribute("user", usermodel); // store user in session
            return "dashboard"; // return dashboard page if login is successful
        } catch (SQLException e) {
            model.addAttribute("error", "Username or password incorrect"); // add error message to model
            return "signin"; // return sign in page if login is unsuccessful
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/signin";
    }



}

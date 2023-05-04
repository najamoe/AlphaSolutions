package com.alphaS.alphasolutions.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Controller
public class TaskCompassController {

    @GetMapping("")
    public String Index(){
        return "index";
    }


}

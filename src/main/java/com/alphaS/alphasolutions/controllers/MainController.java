package com.alphaS.alphasolutions.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @GetMapping("")
    public String Index(){
        return "index";
    }


}

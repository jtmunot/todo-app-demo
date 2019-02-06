package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class HelloController {
/*
    @RequestMapping ("/")
    public String index (){
        return "first";
    } */

    @RequestMapping("/hello")
    public String hello(@RequestParam(name="name", required=false, defaultValue="World") String name, 
                        Model model){

        model.addAttribute("name", name);
        model.addAttribute("phrase", "It's a phrase from the hello method");
        return "hello";
    }
}
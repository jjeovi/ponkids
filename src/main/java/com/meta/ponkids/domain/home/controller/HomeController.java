package com.meta.ponkids.domain.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
    
    
    @GetMapping( "/admin/home" )
    public String list( Model model ) {
        
        // S : 필요한 객체 setting
        
        // E : 필요한 객체 setting
        
        return "/admin/home";
    }
    
}

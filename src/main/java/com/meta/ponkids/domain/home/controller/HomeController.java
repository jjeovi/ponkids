package com.meta.ponkids.domain.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    
    
    @GetMapping( "/" )
    public String list( Model model ) {
        
        // S : 필요한 객체 setting
        
        // E : 필요한 객체 setting
        
        return "pon/index";
    }
    
}

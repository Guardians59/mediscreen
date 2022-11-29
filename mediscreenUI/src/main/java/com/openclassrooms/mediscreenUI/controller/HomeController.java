package com.openclassrooms.mediscreenUI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * La classe HomeController est le controller qui permet de gérer l'url d'accueil de
 * notre application.
 * 
 * @author Dylan
 *
 */
@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
	return "home";
    }

}

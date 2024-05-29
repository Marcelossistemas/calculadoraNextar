package br.com.marcelos.desafio.jmotivation.nex.calculadoranex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";  // Retorna a página de login
    }
}
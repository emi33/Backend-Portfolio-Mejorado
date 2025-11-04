package com.portfolio.maker.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Anotación para marcar esta clase como un controlador REST
public class HelloWorld {

    /**
     * Define un endpoint que responde a peticiones GET en la ruta "/hello".
     * @return Un saludo simple.
     */
    @GetMapping("/hello")
    public String helloWorld() {
        return "¡Hola Mundo desde Spring Boot!";
    }
}

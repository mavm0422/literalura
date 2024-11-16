package com.alurachallenge.literalura.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration

public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a todas las rutas
                .allowedOrigins("http://127.0.0.1:5500/") // Permite solo este origen (puedes agregar varios)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT") // Métodos HTTP permitidos
                .allowedHeaders("*") // Permite todos los encabezados
                .allowCredentials(true); // Permite el uso de credenciales (cookies, autenticación)
    }
}

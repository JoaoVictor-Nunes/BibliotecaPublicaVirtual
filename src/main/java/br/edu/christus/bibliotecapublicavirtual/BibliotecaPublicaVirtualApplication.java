package br.edu.christus.bibliotecapublicavirtual;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Backend - API User-Adress",
                version = "1.0",
                description = "Trata-se de uma Documentação completa da API para gerenciamento de livros e upload de PDFs no MinIO.",
                contact = @Contact(
                        name = "Vasco",
                        email = "vasco@dev.com",
                        url = "https://www.vasco.com"
                )
        )
)
@SpringBootApplication
public class BibliotecaPublicaVirtualApplication {


    public static void main(String[] args) {
        SpringApplication.run(BibliotecaPublicaVirtualApplication.class, args);
    }

}

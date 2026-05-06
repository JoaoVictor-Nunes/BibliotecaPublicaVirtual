package br.edu.christus.bibliotecapublicavirtual.domain.dto;

import br.edu.christus.bibliotecapublicavirtual.domain.model.Serie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDTO {
    private Long id;

    private String name;

   private Serie serie;

    private String email;

    private String password;

}

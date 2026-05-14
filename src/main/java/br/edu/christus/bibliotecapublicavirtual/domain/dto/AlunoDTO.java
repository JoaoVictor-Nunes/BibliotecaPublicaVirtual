package br.edu.christus.bibliotecapublicavirtual.domain.dto;

import br.edu.christus.bibliotecapublicavirtual.domain.model.Serie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

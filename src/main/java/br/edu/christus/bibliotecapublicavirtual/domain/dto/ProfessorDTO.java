package br.edu.christus.bibliotecapublicavirtual.domain.dto;

import br.edu.christus.bibliotecapublicavirtual.domain.model.Serie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import br.edu.christus.bibliotecapublicavirtual.domain.model.Disciplina;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Serie serie;
    private Disciplina disciplina;
}

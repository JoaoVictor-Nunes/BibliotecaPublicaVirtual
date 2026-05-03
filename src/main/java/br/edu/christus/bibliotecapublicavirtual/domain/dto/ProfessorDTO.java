package br.edu.christus.bibliotecapublicavirtual.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private int serie;
    private String materia;
}

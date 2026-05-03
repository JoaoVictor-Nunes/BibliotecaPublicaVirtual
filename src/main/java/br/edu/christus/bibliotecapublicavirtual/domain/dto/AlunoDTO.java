package br.edu.christus.bibliotecapublicavirtual.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDTO {
    private Long id;
    private String name;
    private int biografia;
    private String email;
    private String password;

}

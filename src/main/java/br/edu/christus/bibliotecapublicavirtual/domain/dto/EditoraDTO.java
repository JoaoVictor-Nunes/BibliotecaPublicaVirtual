package br.edu.christus.bibliotecapublicavirtual.domain.dto;


import br.edu.christus.bibliotecapublicavirtual.domain.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditoraDTO {
    private Long id;
    private String cnpj;
    private String name;
    private String email;
    private String endereco;
    private List<Book> Livros;
}

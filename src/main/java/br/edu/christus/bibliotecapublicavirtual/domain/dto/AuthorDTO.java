package br.edu.christus.bibliotecapublicavirtual.domain.dto;

import br.edu.christus.bibliotecapublicavirtual.domain.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private Long id;
    private String name;
    private String email;
    private String biografia;
    private String nacionalidade;
    private List<Book> Livros;
}

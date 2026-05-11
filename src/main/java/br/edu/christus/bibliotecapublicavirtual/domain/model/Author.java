package br.edu.christus.bibliotecapublicavirtual.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_autor")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nacionalidade;

    @Column(nullable = false)
    private String biografia;

    @ManyToMany
    @JoinTable(name = "tb_autor_livro", joinColumns = @JoinColumn(name = "autor_id"), inverseJoinColumns = @JoinColumn(name = "livro_isbn"))
    private List<Book> livros;
}

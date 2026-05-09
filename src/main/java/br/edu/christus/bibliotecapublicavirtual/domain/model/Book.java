package br.edu.christus.bibliotecapublicavirtual.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tb_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String genero;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private int anoLancamento;

    @Column(name = "pdf_key")
    private String pdfKey;

}

package br.edu.christus.bibliotecapublicavirtual.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long isbn;
    private String title;
    private String descricao;
    private int anoLancamento;
    private String genero;
    private String pdfKey;
}
